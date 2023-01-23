package VoxelTutorial;

import Entities.Camera;
import Entities.Entity;
import Models.CubeModel;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import Shaders.StaticShader;
import Textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGameLoop {

    public static Loader loader1 = null;
    public static StaticShader shader1 = null;

    static List<Chunk> chunks = Collections.synchronizedList(new ArrayList<Chunk>());
    static Vector3f camPos = new Vector3f(0, 0, 0);
    static List<Vector3f> usedPos = new ArrayList<Vector3f>();

    static final int WORLD_SIZE = 50;


    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        loader1 = loader;
        StaticShader shader = new StaticShader();
        shader1 = shader;
        MasterRenderer renderer = new MasterRenderer();



        RawModel model = loader1.loadToVAO(CubeModel.vertices, CubeModel.indices, CubeModel.uv);
        ModelTexture texture = new ModelTexture(loader.loadTexture("dirtTex"));
        TexturedModel texModel = new TexturedModel(model, texture);


        Camera camera = new Camera(new Vector3f(0, 0, 0), 0, 0, 0);

        new Thread(new Runnable() {
            public void run() {
                while (!Display.isCloseRequested()) {
                    for (int x = (int) (camPos.x - WORLD_SIZE)/16; x < (camPos.x + WORLD_SIZE)/16; ++x) {
                        for (int z = (int) (camPos.z - WORLD_SIZE)/16; z < (camPos.z + WORLD_SIZE)/16; ++z) {
                            if (!usedPos.contains(new Vector3f(x*16, 0, z*16))) {
                                List<Entity> blocks = new ArrayList<Entity>();

                                for(int i=0;  i<16; ++i){
                                    for(int j=0; j<16; ++j){
                                        blocks.add(new Entity(texModel, new Vector3f(x*16+i, 0, z*16+j), 0, 0, 0, 1));
                                    }
                                }
                                chunks.add(new Chunk(blocks, new Vector3f(x*16, 0, z*16)));
                                usedPos.add(new Vector3f(x*16, 0, z*16));
                            }
                        }
                    }
                }
            }
        }).start();


        while (!Display.isCloseRequested()) {
            camera.move();
            camPos = camera.getPosition();


            for (int i = 0; i < chunks.size(); ++i) {

                Vector3f origin = chunks.get(i).getOrigin();
                int distX = (int) (camPos.x - origin.x);
                int distZ = (int) (camPos.z - origin.z);

                if (distX < 0) {
                    distX = -distX;
                }
                if (distZ < 0) {
                    distZ = -distZ;
                }

                if ((distX <= WORLD_SIZE) || (distZ <= WORLD_SIZE)) {
                    for(int j=0; j<chunks.get(i).getBlocks().size(); ++j)
                    {
                        renderer.addEntity(chunks.get(i).getBlocks().get(j));
                    }
                }
            }
            renderer.render(camera);

            DisplayManager.updateDisplay();

        }
        DisplayManager.closeDisplay();
    }
}

