package com.points.dependencies;

import java.util.HashSet;
import java.util.Set;

public class Component {
	private String name;
	private Set<String> dependsOn;
	private Set<String> requiredFor;
	
	private Component() {}
	
	public Component(String name) {
		this.name = name;
		this.dependsOn = new HashSet<>();
		this.requiredFor = new HashSet<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addDependency(String program) {
		dependsOn.add(program);
	}
	
	public void addRequirement(String program) {
		requiredFor.add(program);
	}
	
	public Set<String> getDependsOn() {
		return dependsOn;
	}
	
	public Set<String> getRequiredFor() {
		return requiredFor;
	}
	
	@Override
	public boolean equals(Object other ) {
		if (other.getClass() != this.getClass() ) {
			return false;
		}
		
		Component second = (Component)other;
		if (second.getName().equals(name) ) {
			return true;
		}
		
		return false;
	}
	
}
