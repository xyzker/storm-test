package stormapplied.creditcard;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import stormapplied.creditcard.topology.AuthorizeCreditCard;
import stormapplied.creditcard.topology.ProcessedOrderNotification;
import stormapplied.creditcard.topology.Spout;
import stormapplied.creditcard.topology.VerifyOrderStatus;

/**
 * @author kexi
 * @since 2017/8/21
 */
public class CreditCardTopologyBuilder {
    public static StormTopology build() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("rabbitmq-spout", new Spout(), 1);
        builder.setBolt("check-status", new VerifyOrderStatus(), 1)
                .shuffleGrouping("rabbitmq-spout")
                .setNumTasks(2);
        builder.setBolt("authorize-card", new AuthorizeCreditCard(), 1)
                .shuffleGrouping("check-status")
                .setNumTasks(2);
        builder.setBolt("notification", new ProcessedOrderNotification(), 1)
                .shuffleGrouping("authorize-card")
                .setNumTasks(1);
        return builder.createTopology();
    }
}
