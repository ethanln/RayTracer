package shader;

public class ShaderFactory {
	
	public enum ShaderType{
		REFLECTION,
		REFRACTION,
		AMBIENT
	};
	
	private static ShaderFactory inst;
	
	private ShaderFactory(){}
	
	private static ShaderFactory instance(){
		if(inst == null){
			inst = new ShaderFactory();
		}
		return inst;
	}
	
	private Shader _makeShader(ShaderType type){
		
		switch(type){
			case REFLECTION:
				return new ReflectionShader();
			case REFRACTION:
				return new RefractionShader();
			case AMBIENT:
				return new AmbientShader();
			default:
				return null;
		}
	}
	
	public static Shader makeShader(ShaderType type){
		return instance()._makeShader(type);
	}
	
}
