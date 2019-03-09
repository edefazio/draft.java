package draft.java.macro;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import draft.java._type;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface _promote {

    Macro $ = new Macro((String)null);

    String value() default "";

    class Macro implements _macro<_type> {

        public String packageName;

        public Macro( _promote _pr){ this.packageName = _pr.value(); }

        public Macro( String packageName ){
            this.packageName = packageName;
        }

        @Override
        public _type apply(_type _t) {
            return to( _t, packageName );
        }

        public static <T extends _type> T to( T _model ){
            return to( _model, null );
        }

        public static <T extends _type> T to( T _model, String packageName ){
            _model.setPublic();
            _model.getModifiers().setStatic(false); //top level entities cannot be static
            CompilationUnit cu = new CompilationUnit();
            cu.addType( (TypeDeclaration)_model.astMember());
            if( packageName != null && !packageName.isEmpty()){
                cu.setPackageDeclaration(packageName);
            }
            _model.removeAnnos(_promote.class);
            return _model;
        }
    }
}
