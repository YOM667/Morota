package me.youm.morota.client.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.youm.morota.Morota;
import me.youm.morota.client.command.Command;
import me.youm.morota.client.command.CommandExecutor;
import me.youm.morota.world.player.capability.MorotaEnergyCapabilityProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

/**
 * @author YouM
 * Created on 2024/2/3
 */
@Command(
        name = "morota",
        description = "revise morota energy"
)
public class MorotaEnergyCommand extends CommandExecutor {
    public MorotaEnergyCommand(CommandDispatcher<CommandSourceStack> commandDispatcher){
        super(commandDispatcher);
        commandDispatcher.register(Commands.literal(Morota.MOD_ID)
                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                .then(Commands.literal("energy")
                        .then(Commands.literal("set")
                                .then(Commands.argument("targets", EntityArgument.player())
                                        .then(Commands.argument("value", IntegerArgumentType.integer(1,1000000))
                                                .executes(context -> setEnergy(
                                                        EntityArgument.getPlayer(context,"targets"),
                                                        context.getSource(),
                                                        IntegerArgumentType.getInteger(context, "value"))
                                                )
                                        )
                                )
                        )
                        .then(Commands.literal("get")
                                .then(Commands.argument("targets", EntityArgument.player())
                                        .executes(context -> getEnergy(
                                                EntityArgument.getPlayer(context,"targets"),
                                                context.getSource()
                                        ))
                                )
                        )
                )
        );
    }

    private int setEnergy(Player player,CommandSourceStack stack,int value) {
        if(player == null){
            stack.sendFailure(new TextComponent("execute field, target player not exist!"));
            return 1;
        }
        player.getCapability(MorotaEnergyCapabilityProvider.MOROTA_ENERGY_CAPABILITY).ifPresent(capability -> {
            capability.setMorotaEnergy(value);
            stack.sendSuccess(new TextComponent("have been set energy: " + capability.getMorotaEnergy()),true);
        });
        return 0;
    }
    private int getEnergy(Player player,CommandSourceStack stack) {
        if(player == null){
            stack.sendFailure(new TextComponent("execute field, target player not exist!"));
            return 1;
        }
        player.getCapability(MorotaEnergyCapabilityProvider.MOROTA_ENERGY_CAPABILITY).ifPresent(capability -> {
            stack.sendSuccess(new TextComponent("player energy: " + capability.getMorotaEnergy()),true);
        });
        return 0;
    }
}