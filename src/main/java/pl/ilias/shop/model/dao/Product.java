package pl.ilias.shop.model.dao;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;

@Entity
@Data
@SuperBuilder
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Auditable implements IdentifiedDataSerializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @ManyToOne
    private Producer producer;
    private Integer quantity;
    private String imagePath;

    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 1;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeLong(id);
        objectDataOutput.writeString(name);
        objectDataOutput.writeString(description);
        objectDataOutput.writeObject(price);
        objectDataOutput.writeInt(quantity);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        id = objectDataInput.readLong();
        name = objectDataInput.readString();
        description = objectDataInput.readString();
        price = objectDataInput.readObject();
        quantity = objectDataInput.readInt();
    }
}
