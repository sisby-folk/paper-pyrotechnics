package folk.sisby.paper_pyrotechnics;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;

public class FireLanceItem extends Item {
	public FireLanceItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType != ClickType.RIGHT) {
			return false;
		} else {
			if (!stack.contains(PaperPyrotechnics.PYROTECHNICS_LOADED) && otherStack.isOf(Items.GUNPOWDER)) {
				slot.getStack().set(PaperPyrotechnics.PYROTECHNICS_LOADED, new LoadedPyrotechnicsComponent(1));
				otherStack.decrement(1);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (stack.contains(PaperPyrotechnics.PYROTECHNICS_LOADED)) {
			target.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE.value());
			target.damage(attacker.getDamageSources().explosion(null, attacker), 8.0F);
			stack.remove(PaperPyrotechnics.PYROTECHNICS_LOADED);
		}
		return super.postHit(stack, target, attacker);
	}

	@Override
	public Text getName(ItemStack stack) {
		return stack.contains(PaperPyrotechnics.PYROTECHNICS_LOADED) ? Text.translatable("item.paper_pyrotechnics.fire_lance.loaded").setStyle(super.getName(stack).getStyle()) : super.getName(stack);
	}
}
