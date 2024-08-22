package jp.axer.cocoainput.mixin;

import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BookEditScreen.class)
public interface BookEditScreenAccessor {
    @Accessor("frameTick")
    public void setFrameTick(int frameTick);
}
