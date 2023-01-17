package VoxelTutorial;

import Models.RawModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import Shaders.StaticShader;
import org.lwjgl.opengl.Display;

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

        RawModel model = loader1.loadToVAO(vertices, indices);

        while(!Display.isCloseRequested())
        {
            renderer.prepare();

            shader.start();
            renderer.render(model);
            shader.stop();
            DisplayManager.updateDisplay();


        }
        DisplayManager.closeDisplay();
    }
}
