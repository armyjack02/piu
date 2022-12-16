package fzzyhmstrs.pack_it_up.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;

public interface Packable {

    String INVENTORY = "pack_inventory";

    static void saveInventory(ItemStack stack, PackInventory inventory){
        NbtCompound stackNbt = stack.getOrCreateNbt();
        NbtCompound inventoryNbt = new NbtCompound();
        inventory.toNbt(inventoryNbt);
        stackNbt.put(INVENTORY,inventoryNbt);
    }

    static void saveInventory(ItemStack stack, PackInventory inventory, PackItem.StackPredicate newPredicate){
        NbtCompound stackNbt = stack.getOrCreateNbt();
        NbtCompound inventoryNbt = new NbtCompound();
        inventory.toNbt(inventoryNbt, newPredicate);
        stackNbt.put(INVENTORY,inventoryNbt);
    }

    static PackInventory getInventory(ItemStack stack, int slots, PackItem.StackPredicate predicate){
        NbtCompound nbt = stack.getNbt();
        if (nbt == null){
            return new PackInventory(slots, predicate);
        }
        if (!nbt.contains(INVENTORY)){
            return new PackInventory(slots, predicate);
        }
        NbtCompound packInv = nbt.getCompound(INVENTORY);
        return PackInventory.fromNbt(slots, packInv);
    }

    void openPackScreenHandler(PlayerEntity user, ItemStack stack);

    void openPackScreenHandler(PlayerEntity user, PackItem.ModuleTier tier, PackItem.StackPredicate stackPredicate, ItemStack stack);
}
