package task_11;

import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.LADD;
import static org.objectweb.asm.Opcodes.LSUB;
import static org.objectweb.asm.Opcodes.PUTSTATIC;
import static org.objectweb.asm.Opcodes.RETURN;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddTimerClassVisitor extends ClassVisitor {
    private String mOwner;
    protected AddTimerClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mOwner= name;
    }

    @Override
    public void visitEnd() {
        FieldVisitor fv = cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "timer", "J", null, null
        );
        if (fv!=null){
            fv.visitEnd();
        }
        cv.visitEnd();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);

        if (methodVisitor != null && !name.equals("<init>")) {
            return new MethodVisitor(api, methodVisitor) {
                @Override
                public void visitCode() {
                    mv.visitCode();

                    mv.visitFieldInsn(GETSTATIC, mOwner, "timer", "J");
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                            "currentTimeMillis", "()J");
                    mv.visitInsn(LSUB);
                    mv.visitFieldInsn(PUTSTATIC, mOwner, "timer", "J");

                }

                @Override
                public void visitInsn(int opcode) {

                    if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                        mv.visitFieldInsn(GETSTATIC, mOwner, "timer", "J");
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                                "currentTimeMillis", "()J");
                        mv.visitInsn(LADD);
                        mv.visitFieldInsn(PUTSTATIC, mOwner, "timer", "J");
                    }
                    mv.visitInsn(opcode);

                }
            };
        }

        return methodVisitor;
    }
}
