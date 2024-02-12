package me.youm.morota.sever.command;

import com.mojang.brigadier.CommandDispatcher;
import me.youm.morota.sever.command.impl.MorotaEnergyCommand;
import net.minecraft.commands.CommandSourceStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YouM
 * Created on 2024/2/3
 */
public class CommandManager {
    public final List<CommandExecutor> commandExecutors = new ArrayList<>();
    public void load(CommandDispatcher<CommandSourceStack> dispatcher) {
        commandExecutors.add(new MorotaEnergyCommand(dispatcher));
    }
}
