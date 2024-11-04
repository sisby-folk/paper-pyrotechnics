package folk.sisby.paper_pyrotechnics.client;

import folk.sisby.paper_pyrotechnics.PaperPyrotechnics;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class PaperPyrotechnicsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModelPredicateProviderRegistry.register(PaperPyrotechnics.FIRE_LANCE, Identifier.of(PaperPyrotechnics.ID, "pyrotechnics_loaded"),
			(stack, world, entity, i) -> stack.contains(PaperPyrotechnics.PYROTECHNICS_LOADED) ? 1.0F : 0.0F
		);
	}
}
