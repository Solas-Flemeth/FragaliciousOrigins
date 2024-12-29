package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

public class MobsUtil {
    public static void spawnChicken(Player player) {
        Chicken mob = player.getWorld().spawn(player.getLocation(), Chicken.class);
        setBaby(mob);
    }
    public static void spawnParrot(Player player, Parrot.Variant variant) {
        Parrot mob = (Parrot) player.getWorld().spawnEntity(player.getLocation(), EntityType.PARROT);
        setBaby(mob);
    }
    public static void spawnCow(Player player) {
        Cow mob = player.getWorld().spawn(player.getLocation(), Cow.class);
        setBaby(mob);
    }
    public static void spawnPig(Player player) {
        Pig mob = player.getWorld().spawn(player.getLocation(), Pig.class);
        setBaby(mob);
    }
    public static void spawnSheep(Player player) {
        Sheep mob = player.getWorld().spawn(player.getLocation(), Sheep.class);
        setBaby(mob);
    }
    public static void spawnWolf(Player player) {
        Wolf mob = player.getWorld().spawn(player.getLocation(), Wolf.class);
        setBaby(mob);
    }
    public static void spawnCat(Player player) {
        Cat mob = player.getWorld().spawn(player.getLocation(), Cat.class);
        setBaby(mob);
    }
    public static void spawnHorse(Player player) {
        Horse mob = player.getWorld().spawn(player.getLocation(), Horse.class);
        setBaby(mob);
    }
    public static void spawnFrog(Player player) {
        Frog mob = player.getWorld().spawn(player.getLocation(), Frog.class);
        setBaby(mob);
    }
    //SPECIAL ANIMALS
    public static void spawnPanda(Player player) {
        Panda mob = player.getWorld().spawn(player.getLocation(), Panda.class);
        setBaby(mob);
    }
    public static void spawnBear(Player player) {
        PolarBear mob = player.getWorld().spawn(player.getLocation(), PolarBear.class);
        setBaby(mob);
    }
    public static void spawnCamel(Player player) {
        Camel mob = player.getWorld().spawn(player.getLocation(), Camel.class);
        setBaby(mob);
    }
    public static void spawnAxolotl(Player player) {
        Axolotl mob = player.getWorld().spawn(player.getLocation(), Axolotl.class);
        setBaby(mob);
    }
    public static void spawnSalmon(Player player) {
        Salmon mob = player.getWorld().spawn(player.getLocation(), Salmon.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnCod(Player player) {
        Cod mob = player.getWorld().spawn(player.getLocation(), Cod.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnPufferfish(Player player) {
        PufferFish mob = player.getWorld().spawn(player.getLocation(), PufferFish.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnTropicalFish(Player player) {
        TropicalFish mob = player.getWorld().spawn(player.getLocation(), TropicalFish.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnStrider(Player player) {
        Strider mob = player.getWorld().spawn(player.getLocation(), Strider.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnFox(Player player) {
        Fox mob = player.getWorld().spawn(player.getLocation(), Fox.class);
        setBaby(mob);
    }

    public static void spawnWanderingTrader(Player player){
        WanderingTrader mob = player.getWorld().spawn(player.getLocation(), WanderingTrader.class);
        setBaby(mob);
    }
    public static void spawnLlama(Player player) {
        Llama mob = player.getWorld().spawn(player.getLocation(), Llama.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    //HOSTILES
    public static void spawnPhantom(Player player) {
        Phantom mob = player.getWorld().spawn(player.getLocation(), Phantom.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnCreeper(Player player) {
        Creeper mob = player.getWorld().spawn(player.getLocation(), Creeper.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
        if(Math.random() < 0.25){
            mob.setPowered(true);
        }
    }
    public static void spawnZombie(Player player) {
        Zombie mob = player.getWorld().spawn(player.getLocation(), Zombie.class);
        setBaby(mob);
    }
    public static void spawnSkeleton(Player player) {
        Zombie mob = player.getWorld().spawn(player.getLocation(), Zombie.class);
        setBaby(mob);
    }
    public static void spawnWitch(Player player) {
        Witch mob = player.getWorld().spawn(player.getLocation(), Witch.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnSpider(Player player) {
        Spider mob = player.getWorld().spawn(player.getLocation(), Spider.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnBlaze(Player player) {
        Blaze mob = player.getWorld().spawn(player.getLocation(), Blaze.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    //BOSSES
    public static void spawnWarden(Player player){
        Warden mob = player.getWorld().spawn(player.getLocation(), Warden.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnWither(Player player){
        Wither mob = player.getWorld().spawn(player.getLocation(), Wither.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnBreeze(Player player) {
        Breeze mob = player.getWorld().spawn(player.getLocation(), Breeze.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnShulker(Player player) {
        Shulker mob = player.getWorld().spawn(player.getLocation(), Shulker.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnElderGuardian(Player player) {
        ElderGuardian mob = player.getWorld().spawn(player.getLocation(), ElderGuardian.class);
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
    }
    public static void spawnKillerRabbit(Player player) {
        Rabbit mob = player.getWorld().spawn(player.getLocation(), Rabbit.class);
        mob.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
        setBaby(mob);
    }
    //UTIL
    private static void setBaby(Ageable mob){
        PlayerUtils.setAttribute(mob,Attribute.SCALE, 0.5);
        if(Math.random() > 0.4){
            mob.setBaby();
        }
    }
}
