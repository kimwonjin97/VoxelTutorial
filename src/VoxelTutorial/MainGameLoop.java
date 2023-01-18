package VoxelTutorial;

import Entities.Entity;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import Shaders.StaticShader;
import Textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class MainGameLoop {

    public static Loader loader1 = null;
    public static StaticShader shader1 = null;
    public static void main(String[] args) {
        DisplayManager.createDisplay();

        MasterRenderer renderer = new MasterRenderer();
        Loader loader = new Loader();
        loader1 = loader;
        StaticShader shader = new StaticShader();
        shader1 = shader;

        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0,
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        float[] uv = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        RawModel model = loader1.loadToVAO(vertices, indices, uv);
        ModelTexture texture = new ModelTexture(loader.loadTexture("dirtTex"));
        TexturedModel texModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texModel, new Vector3f(0, 0, 0), 0, 0, 0 , 1);

        while(!Display.isCloseRequested())
        {
            renderer.prepare();

            entity.increasePosition(0.001f, 0, 0);
            entity.increaseScale(-0.001f);
            entity.increaseRotation(0, 0, 0.01f);

            shader.start();
            renderer.render(entity, shader);
            shader.stop();

            DisplayManager.updateDisplay();

        }
        DisplayManager.closeDisplay();
    }
}
