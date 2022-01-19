package TpSMA;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class MainContainer {
    public static void main(String[] args) {
        try {
            Runtime rt=Runtime.instance();
            Properties p=new ExtendedProperties();
            p.setProperty("gui","true");
            ProfileImpl pc=new ProfileImpl(p);
            AgentContainer container =rt.createMainContainer(pc);
            container.start();
        } catch (ControllerException ex) {
            Logger.getLogger(MainContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
