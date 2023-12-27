package task_11;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.FileInputStream;
import java.util.List;

public class TreeApiTest {
    public static void main(String[] args) throws Exception {
        Class clazz = User.class;
        String clazzFilePath = Utils.getClassFilePath(clazz);
        System.out.println("clazzFilePath: "+ clazzFilePath);
        ClassReader classReader = new ClassReader(new FileInputStream(clazzFilePath));
        ClassNode classNode = new ClassNode(Opcodes.ASM9);
        classReader.accept(classNode, 0);

        List<MethodNode> methods = classNode.methods;
        List<FieldNode> fields = classNode.fields;

        System.out.println("methods:");
        for (MethodNode methodNode : methods) {
            System.out.println(methodNode.name + ", " + methodNode.desc);
        }

        System.out.println("fields:");
        for (FieldNode fieldNode : fields) {
            System.out.println(fieldNode.name + ", " + fieldNode.desc);
        }

    }
}
