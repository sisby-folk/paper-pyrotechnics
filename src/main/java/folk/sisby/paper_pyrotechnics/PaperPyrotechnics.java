package folk.sisby.paper_pyrotechnics;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaperPyrotechnics implements ModInitializer {
	public static final String ID = "paper_pyrotechnics";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final FireLanceItem FIRE_LANCE = Registry.register(Registries.ITEM, Identifier.of(ID, "fire_lance"), new FireLanceItem(ToolMaterials.IRON, new Item.Settings().rarity(Rarity.RARE)));

	public static final ComponentType<LoadedPyrotechnicsComponent> PYROTECHNICS_LOADED = Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(ID, "pyrotechnics_loaded"),
		ComponentType.<LoadedPyrotechnicsComponent>builder().codec(LoadedPyrotechnicsComponent.CODEC).build()
	);

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(e -> e.addAfter(Items.MACE, FIRE_LANCE));
		LOGGER.info("[Paper Pyrotechnics] BOOM! anyway");
	}
}
