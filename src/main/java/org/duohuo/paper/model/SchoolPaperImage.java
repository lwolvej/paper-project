package org.duohuo.paper.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "school_paper_image_info")
public class SchoolPaperImage implements Serializable {

    private static final long serialVersionUID = -6070726794619774689L;

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_name", columnDefinition = "TEXT")
    private String imageName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data", columnDefinition = "MEDIUMBLOB")
    private byte[] imageData;

    @Column(name = "paper_id")
    private Long paperId;

    public SchoolPaperImage() {
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolPaperImage that = (SchoolPaperImage) o;
        return Objects.equals(imageId, that.imageId) &&
                Objects.equals(imageName, that.imageName) &&
                Arrays.equals(imageData, that.imageData) &&
                Objects.equals(paperId, that.paperId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(imageId, imageName, paperId);
        result = 31 * result + Arrays.hashCode(imageData);
        return result;
    }

    @Override
    public String toString() {
        return "SchoolPaperImage{" +
                "imageId=" + imageId +
                ", imageName='" + imageName + '\'' +
                ", imageData=" + Arrays.toString(imageData) +
                ", paperId=" + paperId +
                '}';
    }
}
