package task_11;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddTimerClassVisitor extends ClassVisitor {
    protected AddTimerClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
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
        MethodVisitor newMethodVisitor = new MethodVisitor(api, methodVisitor){

            @Override
            public void visitCode() {
                super.visitCode();
            }
        };

        return newMethodVisitor;
    }
}
