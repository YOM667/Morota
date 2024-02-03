package me.youm.morota.client.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

/**
 * @author YouM
 * Created on 2024/2/3
 */
public class CommandExecutor {
    private CommandDispatcher<CommandSourceStack> dispatcher;
    public String name = this.getClass().getAnnotation(Command.class).name();
    public String description = this.getClass().getAnnotation(Command.class).description();
    public CommandExecutor(CommandDispatcher<CommandSourceStack> dispatcher) {
        this.dispatcher = dispatcher;
    }

}
