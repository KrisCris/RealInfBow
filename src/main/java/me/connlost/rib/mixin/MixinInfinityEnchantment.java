package me.connlost.rib.mixin;

import me.connlost.rib.RealInfBow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InfinityEnchantment.class)
public class MixinInfinityEnchantment {
    boolean hasPost = false;
    @Inject(method = "<init>", at=@At(value = "RETURN"))
    private void loadedLog(Enchantment.Rarity weight, EquipmentSlot[] slotTypes, CallbackInfo ci){
        if (!hasPost){
            RealInfBow.LOG.info("Mixin Injected!");
            hasPost = true;
        }
    }

    @Inject(method = "canAccept", at = @At(value = "RETURN"), cancellable = true)
    private void allowMending(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if (other instanceof MendingEnchantment) {
            if (!cir.getReturnValue()) {
                cir.setReturnValue(true);
            }
        }
    }
}
