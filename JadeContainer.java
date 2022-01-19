
package TpSMA;

import jade.core.Runtime;
import jade.wrapper.ControllerException;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JadeContainer {
    public static void main (String[] args){
        int Tab []={1,4};
        int valeur=4;
        try {
            Runtime rt=Runtime.instance();
            ProfileImpl pc=new ProfileImpl(false);
            pc.setParameter(ProfileImpl.MAIN_HOST, "localhost");
            AgentContainer ac =rt.createAgentContainer(pc);
            AgentController MainAgent=ac.createNewAgent("MainAgent", "TpSMA.MainAgent", new Object[]{ac,Tab,valeur});
            MainAgent.start();
        } catch (ControllerException ex) {
            Logger.getLogger(JadeContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
