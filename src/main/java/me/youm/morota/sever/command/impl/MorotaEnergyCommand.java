package me.youm.morota.sever.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.youm.morota.Morota;
import me.youm.morota.sever.command.Command;
import me.youm.morota.sever.command.CommandExecutor;
import me.youm.morota.networking.Networking;
import me.youm.morota.networking.packets.ServerMorotaEnergySyncPacket;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapabilityProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
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
                                        .then(Commands.argument("value", IntegerArgumentType.integer(0,1000))
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

    private int setEnergy(ServerPlayer player, CommandSourceStack stack, int value) {
        if(player == null){
            stack.sendFailure(new TextComponent("execute field, target player not exist!"));

            return 1;
        }
        player.getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY).ifPresent(capability -> {
            capability.setMorotaEnergy(value);
            Networking.sendToClient(new ServerMorotaEnergySyncPacket(value),player);
            stack.sendSuccess(new TextComponent("have been set energy: " + capability.getMorotaEnergy()),true);
        });
        return 0;
    }
    private int getEnergy(Player player,CommandSourceStack stack) {
        if(player == null){
            stack.sendFailure(new TextComponent("execute field, target player not exist!"));
            return 1;
        }
        player.getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY).ifPresent(capability -> {
            stack.sendSuccess(new TextComponent("player energy: " + capability.getMorotaEnergy()),true);
        });
        return 0;
    }
}
