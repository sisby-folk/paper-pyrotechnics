package folk.sisby.paper_pyrotechnics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LoadedPyrotechnicsComponent(int charges) {
	public static final Codec<LoadedPyrotechnicsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.fieldOf("charges").forGetter(LoadedPyrotechnicsComponent::charges)
	).apply(instance, LoadedPyrotechnicsComponent::new));
}
