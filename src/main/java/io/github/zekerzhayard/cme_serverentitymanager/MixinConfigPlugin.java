package io.github.zekerzhayard.cme_serverentitymanager;

import java.util.List;
import java.util.Set;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();
        for (MethodNode methodNode : targetClass.methods) {
            if (methodNode.name.equals("<init>")) {
                for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                    if (abstractInsnNode.getOpcode() == Opcodes.PUTFIELD) {
                        FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
                        if (fieldInsnNode.owner.equals(mappingResolver.mapClassName("intermediary", "net.minecraft.class_3509").replace('.', '/')) && fieldInsnNode.name.equals(mappingResolver.mapFieldName("intermediary", "net.minecraft.class_3509", "field_15635", "Ljava/util/List;")) && fieldInsnNode.desc.equals("Ljava/util/List;")) {
                            methodNode.instructions.insertBefore(fieldInsnNode, new InsnNode(Opcodes.POP));
                            methodNode.instructions.insertBefore(fieldInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/zekerzhayard/cme_serverentitymanager/CopyOnWriteArrayListWithMutableIterator", "create", "()Ljava/util/List;", false));
                        }
                    }
                }
            }
        }
    }
}
