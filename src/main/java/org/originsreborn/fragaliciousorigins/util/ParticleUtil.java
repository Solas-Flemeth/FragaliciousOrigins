package org.originsreborn.fragaliciousorigins.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;


public class ParticleUtil {
    /**
     * Generates a circle at the location of the target
     * @param particle
     * @param location
     * @param amount
     * @param radius
     */
    public static void generateCircleParticle(Particle particle, Location location, int amount, double radius){
        double increment = 2 * Math.PI / amount;
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);
            location.getWorld().spawnParticle(particle, x, location.getY(), z, 1, 0, 0, 0, 0);
        }
    }

    /**
     * Generates a sphere of particles around the player
     * @param particle
     * @param center
     * @param amount
     * @param radius
     */
    public static void generateSphereParticle(Particle particle, Location center, int amount, double radius){
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
     * Generate a line of particles between Location1 and Location2
     * @param particle
     * @param location1
     * @param location2
     * @param amount
     */
    public static void generateParticleLine(Particle particle, Location location1, Location location2, int amount){
        Vector direction = location2.toVector().subtract(location1.toVector()).normalize();
        double distance = location1.distance(location2);
        double interval = distance / (amount - 1); // Calculate interval between particles

        for (int i = 0; i < amount; i++) {
            Vector offset = direction.clone().multiply(interval * i);
            Location particleLocation = location1.clone().add(offset);
            spawnParticle(particle, particleLocation);
        }
    }
    public static void generateParticleAtLocation(Particle particle,Location location, int amount){
            location.getWorld().spawnParticle(particle, location, amount, 0.3,0.3,0.1);
    }
    /**
     * Utility for line spawning particle
     * @param particle
     * @param location
     */
    private static void spawnParticle(Particle particle, Location location) {
        location.getWorld().spawnParticle(particle, location, 1, 0, 0, 0, 0);
    }

}
