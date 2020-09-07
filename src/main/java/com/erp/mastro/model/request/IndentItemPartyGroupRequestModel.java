package com.erp.mastro.model.request;

import com.erp.mastro.entities.IndentItemPartyGroup;
import com.erp.mastro.entities.ItemStockDetails;
import com.erp.mastro.entities.Party;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class IndentItemPartyGroupRequestModel {

    private Long partyId;
    private Long indentItemId;
    private Long indentId;

    private List<IndentItemPartyGroupRequestModel.IndentItemPartyGroupRequestModels> indentItemPartyGroupRequestModels = new ArrayList<>();

    public IndentItemPartyGroupRequestModel() {

    }

    public IndentItemPartyGroupRequestModel(ItemStockDetails itemStockDetails) {
        this.indentItemId = itemStockDetails.getId();
        itemStockDetails.getIndentItemPartyGroups().parallelStream().forEach(x -> this.indentItemPartyGroupRequestModels.add(new IndentItemPartyGroupRequestModel.IndentItemPartyGroupRequestModels(x)));

    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class IndentItemPartyGroupRequestModels {

        private Long id;
        private Double rate;
        private Double quantity;
        private Party party;

        public IndentItemPartyGroupRequestModels() {
        }

        public IndentItemPartyGroupRequestModels(IndentItemPartyGroup indentItemPartyGroup) {
            if (indentItemPartyGroup != null) {
                this.id = indentItemPartyGroup.getId();
                this.rate = indentItemPartyGroup.getRate();
                this.party = indentItemPartyGroup.getParty();
                this.quantity = indentItemPartyGroup.getQuantity();
            }
        }
    }

    @Override
    public String toString() {
        return "IndentItemPartyGroupRequestModel{" +
                "partyId=" + partyId +
                ", indentItemId=" + indentItemId +
                ", indentId=" + indentId +
                ", indentItemPartyGroupRequestModels=" + indentItemPartyGroupRequestModels +
                '}';
    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class IndentIteamPartGroupsView {
        private Integer id;
        private String partyname;
        private Double qty;
        private Double rate;
        private String itemname;
        private Long hsnno;
        private String hsnnoo;
        private Double total;
        private String purchaseuom;
        private String baseuom;

    }
}
