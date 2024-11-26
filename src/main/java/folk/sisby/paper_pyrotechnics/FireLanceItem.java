package folk.sisby.paper_pyrotechnics;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FireLanceItem extends ToolItem {
	public static final Identifier LANCE_ENTITY_RANGE_MODIFIER_ID = Identifier.of(PaperPyrotechnics.ID, "lance_entity_range");
	public static final Identifier LANCE_KNOCKBACK_MODIFIER_ID = Identifier.of(PaperPyrotechnics.ID, "lance_knockback");

	public FireLanceItem(ToolMaterial material, Settings settings) {
		super(material, settings
			.attributeModifiers(FireLanceItem.createAttributeModifiers(ToolMaterials.IRON, false))
		);
	}

	public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, boolean loaded) {
		return MiningToolItem.createAttributeModifiers(material, 2.0F, -2.4F)
			.with(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(
				LANCE_ENTITY_RANGE_MODIFIER_ID, 2.0, EntityAttributeModifier.Operation.ADD_VALUE
			), AttributeModifierSlot.MAINHAND)
			.with(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, new EntityAttributeModifier(
				LANCE_KNOCKBACK_MODIFIER_ID, loaded ? 3.0 : -0.8, EntityAttributeModifier.Operation.ADD_VALUE
			), AttributeModifierSlot.MAINHAND);
	}

	public boolean canLoad(ItemStack lance, ItemStack fuel, PlayerEntity player) {
		return !lance.contains(PaperPyrotechnics.PYROTECHNICS_LOADED) && fuel.isOf(Items.GUNPOWDER) && !player.getItemCooldownManager().isCoolingDown(lance.getItem());
	}

	public void load(ItemStack lance, ItemStack fuel, PlayerEntity player) {
		lance.set(PaperPyrotechnics.PYROTECHNICS_LOADED, new LoadedPyrotechnicsComponent(1));
		lance.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, createAttributeModifiers(lance.getItem() instanceof ToolItem ti ? ti.getMaterial() : ToolMaterials.IRON, true));
		fuel.decrement(1);
		player.getItemCooldownManager().set(lance.getItem(), 10);
		player.playSound(SoundEvents.BLOCK_CRAFTER_CRAFT);
	}

	public void unload(ItemStack lance) {
		lance.remove(PaperPyrotechnics.PYROTECHNICS_LOADED);
		lance.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, createAttributeModifiers(lance.getItem() instanceof ToolItem ti ? ti.getMaterial() : ToolMaterials.IRON, false));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		ItemStack otherStack = user.getStackInHand(hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND);
		if (canLoad(stack, otherStack, user)) {
			load(stack, otherStack, user);
			return TypedActionResult.success(stack);
		}
		return super.use(world, user, hand);
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType != ClickType.RIGHT) {
			return false;
		} else {
			if (canLoad(stack, otherStack, player)) {
				load(stack, otherStack, player);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (stack.contains(PaperPyrotechnics.PYROTECHNICS_LOADED)) {
			target.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE.value());
			if (target.getWorld() instanceof ServerWorld sw) {
				sw.spawnParticles(ParticleTypes.EXPLOSION, target.getX(), target.getY() + target.getHeight() / 2.0, target.getZ(), 4, 0.3, 0.3, 0.3, 0);
			}
			target.damage(attacker.getDamageSources().explosion(null, attacker), 8.0F);
			unload(stack);
		}
		return super.postHit(stack, target, attacker);
	}

	@Override
	public Text getName(ItemStack stack) {
		return stack.contains(PaperPyrotechnics.PYROTECHNICS_LOADED) ? Text.translatable("item.paper_pyrotechnics.fire_lance.loaded").setStyle(super.getName(stack).getStyle()) : super.getName(stack);
	}
}
