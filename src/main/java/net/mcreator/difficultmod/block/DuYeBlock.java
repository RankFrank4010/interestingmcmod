
package net.mcreator.difficultmod.block;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Rarity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.BucketItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.block.material.Material;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import net.mcreator.difficultmod.procedures.DuYeDangShengWuWanJiaPengZhuangFangKuaiShiProcedure;
import net.mcreator.difficultmod.SuperdifficultmodModElements;

import java.util.stream.Stream;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;

@SuperdifficultmodModElements.ModElement.Tag
public class DuYeBlock extends SuperdifficultmodModElements.ModElement {
	@ObjectHolder("superdifficultmod:du_ye")
	public static final FlowingFluidBlock block = null;
	@ObjectHolder("superdifficultmod:du_ye_bucket")
	public static final Item bucket = null;
	public static FlowingFluid flowing = null;
	public static FlowingFluid still = null;
	private ForgeFlowingFluid.Properties fluidproperties = null;

	public DuYeBlock(SuperdifficultmodModElements instance) {
		super(instance, 3);
		FMLJavaModLoadingContext.get().getModEventBus().register(new FluidRegisterHandler());
	}

	private static class FluidRegisterHandler {
		@SubscribeEvent
		public void registerFluids(RegistryEvent.Register<Fluid> event) {
			event.getRegistry().register(still);
			event.getRegistry().register(flowing);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void clientLoad(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(still, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(flowing, RenderType.getTranslucent());
	}

	@Override
	public void initElements() {
		fluidproperties = new ForgeFlowingFluid.Properties(() -> still, () -> flowing,
				FluidAttributes
						.builder(new ResourceLocation("superdifficultmod:blocks/duye"), new ResourceLocation("superdifficultmod:blocks/jiashui"))
						.luminosity(5).density(200).viscosity(400).temperature(200)

						.rarity(Rarity.RARE))
				.explosionResistance(100f).canMultiply().tickRate(10).levelDecreasePerBlock(1).slopeFindDistance(4).bucket(() -> bucket)
				.block(() -> block);
		still = (FlowingFluid) new ForgeFlowingFluid.Source(fluidproperties).setRegistryName("du_ye");
		flowing = (FlowingFluid) new ForgeFlowingFluid.Flowing(fluidproperties).setRegistryName("du_ye_flowing");
		elements.blocks.add(() -> new FlowingFluidBlock(still, Block.Properties.create(Material.LAVA).hardnessAndResistance(100f)
				.setNeedsPostProcessing((bs, br, bp) -> true).setEmmisiveRendering((bs, br, bp) -> true).setLightLevel(s -> 0)) {
			@Override
			public void onEntityCollision(BlockState blockstate, World world, BlockPos pos, Entity entity) {
				super.onEntityCollision(blockstate, world, pos, entity);
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();

				DuYeDangShengWuWanJiaPengZhuangFangKuaiShiProcedure.executeProcedure(Stream.of(new AbstractMap.SimpleEntry<>("entity", entity))
						.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
			}
		}.setRegistryName("du_ye"));
		elements.items.add(() -> new BucketItem(still,
				new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE))
				.setRegistryName("du_ye_bucket"));
	}
}
