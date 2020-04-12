package blog.gamedevelopmentbox2dtutorial;

        import com.badlogic.gdx.graphics.g2d.ParticleEffect;
        import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
        import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
        import com.badlogic.gdx.utils.IntMap;

/**
 *  A pooled particle effect manager to store particle effect pools
 *
 */
public class ParticleEffectManager {
    // DEFINE constants for particleEffects
    public static final int SMOKE = 0;
    public static final int DUST = 1;
    public static final int EXPLOSION = 2;
    public static final int BLOOD = 3;
    public static final int SPLASH = 4;
    public static final int SPEED = 5;
    public static final int POWER_UP = 6;
    public static final int BULLET_RIGHT = 7;
    public static final int BULLET_LEFT = 8;
    public static final int SUPERSPEED_RIGHT = 9;
    public static final int SUPERSPEED_LEFT = 10;
    public static final int TEST = 11;
    public static final int POWERUP_SPEED = 12;
    public static final int POWERUP_GUN = 13;






    // create intmaps for effects and pools
    private IntMap<ParticleEffect> partyEffects;
    private IntMap<ParticleEffectPool> partyEffectPool;

    /**
     *  Particle Effect Manager for controlling creating pools and dispensing particle effects
     */
    public ParticleEffectManager(){
        partyEffects = new IntMap<ParticleEffect>();
        partyEffectPool = new IntMap<ParticleEffectPool>();
    }

    /** Create a particle effect pool for type  with default values (scale 1, pool init capacity 5, max capacity 20)
     * @param type int id of particle effect
     * @param party the particle effect
     */
    public void addParticleEffect(int type, ParticleEffect party){
        addParticleEffect(type,party,1);
    }

    /** Create a particle effect pool for type with scale and default pool sizes
     * @param type int id of particle effect
     * @param party the particle effect
     * @param scale The particle effect scale
     */
    public void addParticleEffect(int type, ParticleEffect party, float scale ){
        addParticleEffect(type,party,scale,5,20);

    }

    /** Create a particle effect pool for type
     * @param type int id of particle effect
     * @param party the particle effect
     * @param scale The particle effect scale
     * @param startCapacity pool initial capacity
     * @param maxCapacity pool max capacity
     */
    public void addParticleEffect(int type, ParticleEffect party, float scale, int startCapacity, int maxCapacity){
        party.scaleEffect(scale);
        partyEffects.put(type, party);
        partyEffectPool.put(type,new ParticleEffectPool(party,startCapacity,maxCapacity));

    }

    /**
     *  Get a particle effect of type type
     * @param type the type to get
     * @return The pooled particle effect
     */
    public PooledEffect getPooledParticleEffect(int type){
        return partyEffectPool.get(type).obtain();
    }
}