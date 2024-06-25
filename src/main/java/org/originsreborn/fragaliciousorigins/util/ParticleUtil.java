package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.util.Vector;


public class ParticleUtil {
    /**
     * Generates a circle at the location of the target
     *
     * @param particle
     * @param location
     * @param amount
     * @param radius
     */
    public static void generateCircleParticle(Particle particle, Location location, int amount, double radius) {
        double increment = 2 * Math.PI / amount;
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);
            location.getWorld().spawnParticle(particle, x, location.getY(), z, 1, 0, 0, 0, 0);
        }
    }

    /**
     * Generates a circle at the location of the target using block particles.
     *
     * @param material
     * @param location
     * @param amount
     * @param radius
     */
    public static void generateCircleParticle(Material material, Location location, int amount, double radius) {
        double increment = 2 * Math.PI / amount;
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);
            location.getWorld().spawnParticle(Particle.BLOCK, x, location.getY(), z, 1, 0, 0, 0, 0, material.createBlockData());
        }
    }

    /**
     * Generates a circle at the location of the target using dust transition particles.
     *
     * @param particle
     * @param dustTransition
     * @param location
     * @param amount
     * @param radius
     */
    public static void generateCircleParticle(Particle particle, Particle.DustTransition dustTransition, Location location, int amount, double radius) {
        double increment = 2 * Math.PI / amount;
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);
            location.getWorld().spawnParticle(particle, x, location.getY(), z, 1, 0, 0, 0, 0, dustTransition);
        }
    }

    /**
     * Generates a circle at the location of the target using dust particles.
     *
     * @param particle
     * @param dustOptions
     * @param location
     * @param amount
     * @param radius
     */
    public static void generateCircleParticle(Particle particle, Particle.DustOptions dustOptions, Location location, int amount, double radius) {
        double increment = 2 * Math.PI / amount;
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);
            location.getWorld().spawnParticle(particle, x, location.getY(), z, 1, 0, 0, 0, 0, dustOptions);
        }
    }

    /**
     * Generates a sphere of particles around the player
     *
     * @param particle
     * @param center
     * @param amount
     * @param radius
     */
    public static void generateSphereParticle(Particle particle, Location center, int amount, double radius) {
        for (int i = 0; i < amount; i++) {
            double theta = Math.acos(2 * Math.random() - 1); // Random angle from 0 to pi
            double phi = Math.random() * 2 * Math.PI; // Random angle from 0 to 2pi

            double sinTheta = Math.sin(theta);
            double x = radius * sinTheta * Math.cos(phi);
            double y = radius * Math.cos(theta);
            double z = radius * sinTheta * Math.sin(phi);

            Vector offset = new Vector(x, y, z);
            Location particleLocation = center.clone().add(offset);
            center.getWorld().spawnParticle(particle, particleLocation, 1, 0, 0, 0, 0);
        }
    }

    /**
     * Generates a sphere of block particles around the player.
     *
     * @param material
     * @param center
     * @param amount
     * @param radius
     */
    public static void generateSphereParticle(Material material, Location center, int amount, double radius) {
        for (int i = 0; i < amount; i++) {
            double theta = Math.acos(2 * Math.random() - 1); // Random angle from 0 to pi
            double phi = Math.random() * 2 * Math.PI; // Random angle from 0 to 2pi

            double sinTheta = Math.sin(theta);
            double x = radius * sinTheta * Math.cos(phi);
            double y = radius * Math.cos(theta);
            double z = radius * sinTheta * Math.sin(phi);

            Vector offset = new Vector(x, y, z);
            Location particleLocation = center.clone().add(offset);
            center.getWorld().spawnParticle(Particle.BLOCK, particleLocation, 1, 0, 0, 0, 0, material.createBlockData());
        }
    }


    /**
     * Generates a sphere of dust particles around the player.
     *
     * @param particle
     * @param dustOptions
     * @param center
     * @param amount
     * @param radius
     */
    public static void generateSphereParticle(Particle particle, Particle.DustOptions dustOptions, Location center, int amount, double radius) {
        for (int i = 0; i < amount; i++) {
            double theta = Math.acos(2 * Math.random() - 1); // Random angle from 0 to pi
            double phi = Math.random() * 2 * Math.PI; // Random angle from 0 to 2pi

            double sinTheta = Math.sin(theta);
            double x = radius * sinTheta * Math.cos(phi);
            double y = radius * Math.cos(theta);
            double z = radius * sinTheta * Math.sin(phi);

            Vector offset = new Vector(x, y, z);
            Location particleLocation = center.clone().add(offset);
            center.getWorld().spawnParticle(particle, particleLocation, 1, 0, 0, 0, 0, dustOptions);
        }
    }

    /**
     * Generates a sphere of dust transition particles around the player.
     *
     * @param particle
     * @param dustTransition
     * @param center
     * @param amount
     * @param radius
     */
    public static void generateSphereParticle(Particle particle, Particle.DustTransition dustTransition, Location center, int amount, double radius) {
        for (int i = 0; i < amount; i++) {
            double theta = Math.acos(2 * Math.random() - 1); // Random angle from 0 to pi
            double phi = Math.random() * 2 * Math.PI; // Random angle from 0 to 2pi

            double sinTheta = Math.sin(theta);
            double x = radius * sinTheta * Math.cos(phi);
            double y = radius * Math.cos(theta);
            double z = radius * sinTheta * Math.sin(phi);

            Vector offset = new Vector(x, y, z);
            Location particleLocation = center.clone().add(offset);
            center.getWorld().spawnParticle(particle, particleLocation, 1, 0, 0, 0, 0, dustTransition);
        }
    }

    /**
     * Generate a line of particles between Location1 and Location2
     *
     * @param particle
     * @param location1
     * @param location2
     * @param amount
     */
    public static void generateParticleLine(Particle particle, Location location1, Location location2, int amount) {
        Vector direction = location2.toVector().subtract(location1.toVector()).normalize();
        double distance = location1.distance(location2);
        double interval = distance / (amount - 1); // Calculate interval between particles

        for (int i = 0; i < amount; i++) {
            Vector offset = direction.clone().multiply(interval * i);
            Location particleLocation = location1.clone().add(offset);
            spawnParticle(particle, particleLocation);
        }
    }

    /**
     * Generate a line of particles between Location1 and Location2 using block particles.
     *
     * @param material
     * @param location1
     * @param location2
     * @param amount
     */
    public static void generateParticleLine(Material material, Location location1, Location location2, int amount) {
        Vector direction = location2.toVector().subtract(location1.toVector()).normalize();
        double distance = location1.distance(location2);
        double interval = distance / (amount - 1); // Calculate interval between particles

        for (int i = 0; i < amount; i++) {
            Vector offset = direction.clone().multiply(interval * i);
            Location particleLocation = location1.clone().add(offset);
            spawnParticle(material, particleLocation);
        }
    }

    /**
     * Generate a line of particles between Location1 and Location2 using dust particles.
     *
     * @param particle
     * @param dustOptions
     * @param location1
     * @param location2
     * @param amount
     */
    public static void generateParticleLine(Particle particle, Particle.DustOptions dustOptions, Location location1, Location location2, int amount) {
        Vector direction = location2.toVector().subtract(location1.toVector()).normalize();
        double distance = location1.distance(location2);
        double interval = distance / (amount - 1); // Calculate interval between particles

        for (int i = 0; i < amount; i++) {
            Vector offset = direction.clone().multiply(interval * i);
            Location particleLocation = location1.clone().add(offset);
            spawnParticle(particle, dustOptions, particleLocation);
        }
    }

    /**
     * Generate a line of particles between Location1 and Location2 using dust transition particles.
     *
     * @param particle
     * @param dustTransition
     * @param location1
     * @param location2
     * @param amount
     */
    public static void generateParticleLine(Particle particle, Particle.DustTransition dustTransition, Location location1, Location location2, int amount) {
        Vector direction = location2.toVector().subtract(location1.toVector()).normalize();
        double distance = location1.distance(location2);
        double interval = distance / (amount - 1); // Calculate interval between particles

        for (int i = 0; i < amount; i++) {
            Vector offset = direction.clone().multiply(interval * i);
            Location particleLocation = location1.clone().add(offset);
            spawnParticle(particle, dustTransition, particleLocation);
        }
    }

    /**
     * Generate a particle at a specific location with a mild offset
     *
     * @param particle
     * @param location
     * @param amount
     */
    public static void generateParticleAtLocation(Particle particle, Location location, int amount) {
        location.getWorld().spawnParticle(particle, location, amount, 0.3, 0.3, 0.1);
    }

    /**
     * Generate a particle at a specific location with a mild offset
     *
     * @param material
     * @param location
     * @param amount
     */
    public static void generateParticleAtLocation(Material material, Location location, int amount) {
        location.getWorld().spawnParticle(Particle.BLOCK, location, amount, 0.3, 0.3, 0.1, material.createBlockData());
    }

    /**
     * Generate a particle at a specific location with a mild offset
     *
     * @param particle
     * @param dustOptions
     * @param location
     * @param amount
     */
    public static void generateParticleAtLocation(Particle particle, Particle.DustOptions dustOptions, Location location, int amount) {
        location.getWorld().spawnParticle(particle, location, amount, 0.3, 0.3, 0.1, dustOptions);
    }

    /**
     * Generate a particle at a specific location with a mild offset
     *
     * @param particle
     * @param dustTransition
     * @param location
     * @param amount
     */
    public static void generateParticleAtLocation(Particle particle, Particle.DustTransition dustTransition, Location location, int amount) {
        location.getWorld().spawnParticle(particle, location, amount, 0.3, 0.3, 0.1, dustTransition);
    }

    /**
     * Creates a DustTransition object for custom dust particles.
     *
     * @param color1 The starting color of the dust.
     * @param color2 The ending color of the dust.
     * @param size   The size of the dust particles.
     * @return A Particle.DustTransition object with the specified properties.
     */
    public static Particle.DustTransition dustTransition(int color1, int color2, float size) {
        return new Particle.DustTransition(Color.fromRGB(color1), Color.fromRGB(color2), size);
    }

    /**
     * Creates a DustOptions object for custom dust particles.
     *
     * @param color The color of the dust.
     * @param size  The size of the dust particles.
     * @return A Particle.DustOptions object with the specified properties.
     */
    public static Particle.DustOptions dustOptions(int color, float size) {
        return new Particle.DustOptions(Color.fromRGB(color), size);
    }

    /**
     * Utility for line spawning particle
     *
     * @param particle
     * @param location
     */
    private static void spawnParticle(Particle particle, Location location) {
        location.getWorld().spawnParticle(particle, location, 1, 0, 0, 0, 0);
    }

    /**
     * Utility for line spawning block particles.
     *
     * @param material
     * @param location
     */
    private static void spawnParticle( Material material, Location location) {
        location.getWorld().spawnParticle(Particle.BLOCK, location, 1, 0, 0, 0, 0, material.createBlockData());
    }

    /**
     * Utility for line spawning dust particles.
     *
     * @param particle
     * @param dustOptions
     * @param location
     */
    private static void spawnParticle(Particle particle, Particle.DustOptions dustOptions, Location location) {
        location.getWorld().spawnParticle(particle, location, 1, 0, 0, 0, 0, dustOptions);
    }

    /**
     * Utility for line spawning dust transition particles.
     *
     * @param particle
     * @param dustTransition
     * @param location
     */
    private static void spawnParticle(Particle particle, Particle.DustTransition dustTransition, Location location) {
        location.getWorld().spawnParticle(particle, location, 1, 0, 0, 0, 0, dustTransition);
    }

}
