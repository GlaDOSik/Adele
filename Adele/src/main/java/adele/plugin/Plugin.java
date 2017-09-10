/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adele.plugin;

import adele.utils.Version;
import java.util.List;

/**
 * Basic interface which every plugin has to implement in order to be
 * registered by Adele. Jar file can contain more than one implementation of
 * interface. Each implementation is considered as individual plugin.
 *
 * @author ludek
 */
public interface Plugin {

    /**
     * Returns the name of the plugin
     *
     * @return plugin name
     */
    public String getName();

    /**
     * Returns the version of the plugin
     *
     * @return plugin version
     */
    public Version getVersion();

    /**
     * Returns the name of plugin author
     *
     * @return plugin author name
     */
    public String getAuthor();

    /**
     * Returns the plugin description. It should be very informative and not too
     * long.
     *
     * @return spot on plugin description
     */
    public String getDescription();

    /**
     * Version(s) of Adele this plugin is compiled or tested against and has no
     * compatibility issues.
     *
     * This tells nothing about the real compatibility. It is used only to show
     * the compatibility warning sign in the plugin menu.
     *
     * @return array of compatible Adele versions
     */
    public Version[] compatibleWith();

    /**
     * Get all components related to this plugin. Components are initialized by
     * engine in order their appearance in list. It's plugins developer
     * responsibility to order them in a way that makes sense.
     *
     * @return
     */
    public List<PluginComponent> getPluginComponents();

    //TODO icon?
}
