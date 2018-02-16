package org.enmichuk.ignite.gettingstarted.binary;

import org.apache.ignite.binary.BinaryObjectException;
import org.apache.ignite.binary.BinaryReader;
import org.apache.ignite.binary.BinaryWriter;
import org.apache.ignite.binary.Binarylizable;

/**
 * Employee address.
 * <p>
 * This class implements {@link org.apache.ignite.binary.Binarylizable} only for example purposes,
 * in order to show how to customize serialization and deserialization of
 * binary objects.
 */
public class Address implements Binarylizable {
    private String street;

    private int zip;

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getStreet() {

        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Address() {
        // No-op.
    }

    public Address(String street, int zip) {
        this.street = street;
        this.zip = zip;
    }

    @Override public void writeBinary(BinaryWriter writer) throws BinaryObjectException {
        writer.writeString("street", street);
        writer.writeInt("zip", zip);
    }

    @Override public void readBinary(BinaryReader reader) throws BinaryObjectException {
        street = reader.readString("street");
        zip = reader.readInt("zip");
    }

    @Override public String toString() {
        return "Address:[street=" + street + ", zip=" + zip + ']';
    }
}