package Shaders;

public class StaticShader extends ShaderProgram {

    private static final String vertexFile = "./src/Shaders/vertexShader.txt";
    private static final String fragmentFile = "./src/Shaders/fragmentShader.txt";

    public StaticShader() {
        super(vertexFile, fragmentFile);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute("position", 0);
        super.bindAttribute("textureCoords", 1);
    }

}
