package blog.boomerangbeast.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;

public class AnimationComponent implements Component, Pool.Poolable {
    public IntMap<Animation> animationsN = new IntMap<Animation>();
    public IntMap<Animation> animationsB = new IntMap<Animation>();

    @Override
    public void reset(){
        animationsN = new IntMap<Animation>();
        animationsB = new IntMap<Animation>();


    }

}
