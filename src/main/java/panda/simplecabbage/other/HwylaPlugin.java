package panda.simplecabbage.other;

import javax.annotation.Nonnull;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.item.ItemStack;
import panda.simplecabbage.blocks.BlockCabbage;
import panda.simplecabbage.init.ModItems;

@WailaPlugin
public class HwylaPlugin implements IWailaPlugin {

    @Override
    public void register(IWailaRegistrar registrar) {
        registrar.registerStackProvider(new IWailaDataProvider() {   	
            @Nonnull
            @Override
			public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
            	return new ItemStack(ModItems.CABBAGE_HEAD);
            }
        }, BlockCabbage.class);
    }
    
}