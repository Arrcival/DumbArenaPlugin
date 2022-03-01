package com.arrcival.dumbarenaplugin.gamevents;

import com.arrcival.dumbarenaplugin.utils.Consts;
import com.arrcival.dumbarenaplugin.utils.Statics;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class RandomEffect extends GameEventArena{

    ArrayList<PotionEffect> effectList = new ArrayList<>();

    PotionEffect chosenEffect;

    public RandomEffect()
    {
        effectList.add(new PotionEffect(PotionEffectType.ABSORPTION, 30 * Consts.TICKS_PER_SECOND, 3));
        effectList.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * Consts.TICKS_PER_SECOND, 0));
        effectList.add(new PotionEffect(PotionEffectType.SPEED, 30 * Consts.TICKS_PER_SECOND, 0));
        //effectList.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30 * Consts.TICKS_PER_SECOND, 0));
    }
    @Override
    public void PrepareEvent() {

        int i = Statics.RandomInt(effectList.size(), false);
        chosenEffect = effectList.get(i);

        Statics.CurrentGame.SendMessageToAllAlivePlayers(ChatColor.GREEN + "In " + Consts.TIME_BETWEEN_ANNOUNCE_AND_EVENT + " seconds, every players will receive : ");
        Statics.CurrentGame.SendMessageToAllAlivePlayers(
                ChatColor.GREEN + Integer.toString(chosenEffect.getDuration() / Consts.TICKS_PER_SECOND) +
                        "s of " + Statics.GetPrettyName(chosenEffect.getType().getName()) + " " + (chosenEffect.getAmplifier() + 1));


        RunEventLater();
    }

    @Override
    public void RunEvent() {
        Statics.CurrentGame.PlayerList.forEach(player ->
                player.addPotionEffect(chosenEffect)
                );
    }
}
