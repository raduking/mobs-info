package org.raduking.mobsinfo.hud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.raduking.mobsinfo.details.Details;
import org.raduking.mobsinfo.details.HorseDetails;
import org.raduking.mobsinfo.details.PandaDetails;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class DisplayedInfo {

	private static final Map<Class<? extends LivingEntity>, ? extends Details<? extends LivingEntity>> DETAILS_ASSOCIATION_MAP = new HashMap<>() {{
		put(PandaEntity.class, new PandaDetails());
		put(HorseEntity.class, new HorseDetails());
	}};

	private LivingEntity mobReference;

	private Identifier id;
	private String uuid;
	private String name;
	private float health;
	private float maxHealth;

	private final List<Pair<String, ?>> pairList = new ArrayList<>();

	public void reset() {
		setMobReference(null);

		setId(null);
		setName(null);
		setHealth(0.0f);
		setMaxHealth(0.0f);

		pairList.clear();
	}

	public void update(final LivingEntity mob) {
		if (mob == getMobReference()) {
			return;
		}
		reset();

		setMobReference(mob);
		setBasicInfo(mob);

		Details<LivingEntity> details = getEntityDetails(mob.getClass());
		if (null != details) {
			details.populate(mob, this);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends LivingEntity, U extends Details<T>> U getEntityDetails(final Class<?> cls) {
		return (U) DETAILS_ASSOCIATION_MAP.get(cls);
	}

	public LivingEntity getMobReference() {
		return mobReference;
	}

	public void setMobReference(final LivingEntity mobReference) {
		this.mobReference = mobReference;
	}

	public boolean hasId() {
		return null != id;
	}

	public Identifier getId() {
		return id;
	}

	public void setId(final Identifier id) {
		this.id = id;
		add("mobsinfo.hud.id", id);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
		add("mobsinfo.hud.uuid", uuid);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
		add("mobsinfo.hud.name", name);
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(final float health) {
		this.health = health;
		add("mobsinfo.hud.health", health);
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(final float maxHealth) {
		this.maxHealth = maxHealth;
		add("mobsinfo.hud.max_health", maxHealth);
	}

	public void setBasicInfo(final LivingEntity mob) {
		setUuid(mob.getUuidAsString());
		setId(Registries.ENTITY_TYPE.getId(mob.getType()));

		setName(mob.getName().getString());
		setHealth(mob.getHealth());
		setMaxHealth(mob.getMaxHealth());
	}

	public <T> void add(final String info, final T value) {
		pairList.add(new Pair<>(info, value));
	}

	public List<Pair<String, ?>> getPairList() {
		return pairList;
	}

}
