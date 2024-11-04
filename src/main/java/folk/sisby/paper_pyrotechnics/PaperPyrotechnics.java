package folk.sisby.paper_pyrotechnics;

import net.fabricmc.api.ModInitializer;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaperPyrotechnics implements ModInitializer {
	public static final String ID = "paper_pyrotechnics";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final FireLanceItem FIRE_LANCE = Registry.register(Registries.ITEM, Identifier.of(ID, "fire_lance"), new FireLanceItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));

	public static final ComponentType<LoadedPyrotechnicsComponent> PYROTECHNICS_LOADED = Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(ID, "pyrotechnics_loaded"),
		ComponentType.<LoadedPyrotechnicsComponent>builder().codec(LoadedPyrotechnicsComponent.CODEC).build()
	);

	@Override
	public void onInitialize() {
		LOGGER.info("[Paper Pyrotechnics] BOOM! anyway");
	}
}