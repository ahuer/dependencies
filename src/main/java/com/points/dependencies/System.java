package com.points.dependencies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class System {
	private static final Logger LOG = Logger.getLogger( System.class.getName() );
	
	private Map<String, Component> installedComponents;
	private Map<String, Component> possibleComponents;
	
	public System() {
		installedComponents = new HashMap<>();
		possibleComponents = new HashMap<>();
	}
	
	public Map<String, Component> getInstalledComponents() {
		return installedComponents;
	}
	
	public Map<String, Component> getPossibleComponents() {
		return possibleComponents;
	}
	
	public void processInput(List<String> inputList) {
		for (String item : inputList ) {
			LOG.info(item);
			String[] commands = item.split(" ");
			
			switch (commands[0] ) {
			case "DEPEND":
				// DEPEND item1 item2...
				if (commands.length < 3 ) {
					LOG.warning("Could not add dependencies for: [" + commands.toString() + "], too few items, skipping line...");
					continue;
				}				
				addDependencies(commands);
				continue;
				
			case "INSTALL":
				if (commands.length != 2 ) {
					LOG.warning("Could not install component: [" + commands.toString() + "], is not 2 items, skipping line...");
					continue;
				}
				installComponentAndDependencies(commands[1]);
				continue;
				
			case "REMOVE":
				if (commands.length != 2 ) {
					LOG.warning("Could not remove component: [" + commands.toString() + "], is not 2 items, skipping line...");
					continue;
				} 
				removeComponentAndDependencies(commands[1]);
				continue;
				
			case "LIST":
				logInstalledComponents();
				continue;
			
			case "END":
				LOG.info("END reached, exiting...");
				break;
			default:
				LOG.warning("Received invalid input: [" + commands[0] + "], skipping line...");
				continue;
			}
		}
	}
	
	private void addDependencies(String[] components ) {
		if (components == null || components.length == 0 ) {
			LOG.warning("Could not add dependency: [" + components + "], was empty or null, skipping line...");
			return;
		}
		
		Component item1 = new Component(components[1]);
		if (!possibleComponents.containsKey(item1) ) {
			possibleComponents.put(item1.getName(), item1);
		}
		
		for (int i = 2; i < components.length; i++ ) {
			Component nextItem = new Component(components[i]);
			if (!possibleComponents.containsKey(nextItem) ) {
				possibleComponents.put(nextItem.getName(), nextItem);
			}
			
			item1.addDependency(nextItem.getName());
			nextItem.addRequirement(item1.getName());
		}		
	}
	
	private void installComponentAndDependencies(String name) {
		if (name == null || name.isEmpty() ) {
			LOG.warning("Could not install component: [" + name + "], is empty or null, skipping line...");
			return;
		}
		
		Component newComponent = new Component(name);
		
		if (!possibleComponents.containsKey(name) ) {
			possibleComponents.put(name, newComponent);
		} else {
			newComponent = possibleComponents.get(name);
		}
		
		if (installedComponents.containsKey(name) ) {
			LOG.info("Component already installed: [" + name + "]");
			return;
		}
		
		Set<String> dependencies = newComponent.getDependsOn();
		if (dependencies != null ) {
			for (String item : dependencies ) {
				installComponentAndDependencies(item);
			}			
		}
		LOG.info("Installing " + name);
		installedComponents.put(name, newComponent);
	}
	
	public void removeComponentAndDependencies(String name) {
		if (name == null || name.isEmpty() ) {			
			LOG.warning("Could not remove component: [" + name + "], name is empty or null, skipping line...");
			return;
		}
		
		if (!installedComponents.containsKey(name) ) {
			LOG.info("Could not remove component: [" + name + "], component not installed");
			return;
		}
		
		Component component = installedComponents.get(name);
		Set<String> requiredFor = component.getRequiredFor();
		
		for (String item : requiredFor ) {
			if (installedComponents.containsKey(item) ) {
				LOG.info("Could not remove component: [" + name + "], required component still installed: [" + item + "]");
				return;
			}
		}
		
		LOG.info("Removing " + name);
		installedComponents.remove(name);		
		Set<String> dependencies = component.getDependsOn();
		
		for (String item : dependencies ) {
			removeComponentAndDependencies(item);
		}		
	}
	
	public void logInstalledComponents() {
		LOG.info("Listing installed components...");
		for (String item : installedComponents.keySet() ) {
			LOG.info(item);
		}
	}
	
}
