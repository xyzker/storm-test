package stormapplied.creditcard;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;

/**
 * @author kexi
 * @since 2017/8/21
 */
public class RemoteTopologyRunner {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        StormTopology topology = CreditCardTopologyBuilder.build();

        Config config = new Config();
        config.setNumWorkers(2);
        config.setMessageTimeoutSecs(60);

        StormSubmitter.submitTopology("credit-card-topology", config, topology);
    }

}
