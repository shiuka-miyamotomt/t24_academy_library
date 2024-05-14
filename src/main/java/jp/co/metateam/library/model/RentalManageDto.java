package jp.co.metateam.library.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jp.co.metateam.library.values.RentalStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 貸出管理DTO
 */
@Getter
@Setter
public class RentalManageDto {

    private Long id;

    @NotEmpty(message="在庫管理番号は必須です")
    private String stockId;

    @NotEmpty(message="社員番号は必須です")
    private String employeeId;

    @NotNull(message="貸出ステータスは必須です")
    private Integer status;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="貸出予定日は必須です")
    private Date expectedRentalOn;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="返却予定日は必須です")
    private Date expectedReturnOn;

    private Timestamp rentaledAt;

    private Timestamp returnedAt;

    private Timestamp canceledAt;

    private Stock stock;

    private Account account;

    //貸出ステータスのエラーメッセージ
    public Optional<String> isvalidStatus(Integer preStatus){
        if(preStatus == RentalStatus.RENT_WAIT.getValue() && this.status == RentalStatus.RETURNED.getValue()){
            return Optional.of( "貸出ステータスを貸出待ちから返却済みには変更できません");
        }
        else if(preStatus == RentalStatus.RENTAlING.getValue() && this.status == RentalStatus.RENT_WAIT.getValue()){
            return Optional.of( "貸出ステータスを貸出中から貸出待ちには変更できません");
        }
        else if(preStatus == RentalStatus.RENTAlING.getValue() && this.status == RentalStatus.CANCELED.getValue()){
            return Optional.of( "貸出ステータスを貸出中からキャンセルには変更できません");
        }
        else if(preStatus == RentalStatus.RETURNED.getValue() && this.status == RentalStatus.RENT_WAIT.getValue()){
            return Optional.of( "貸出ステータスを返却済みから貸出待ちには変更できません");
        }
        else if(preStatus == RentalStatus.RETURNED.getValue() && this.status == RentalStatus.RENTAlING.getValue()){
            return Optional.of( "貸出ステータスを返却済みから貸出中には変更できません");
        }
        else if(preStatus == RentalStatus.RETURNED.getValue() && this.status == RentalStatus.CANCELED.getValue()){
            return Optional.of( "貸出ステータスを返却済みからキャンセルには変更できません");
        }
        else if(preStatus == RentalStatus.CANCELED.getValue() && this.status == RentalStatus.RENT_WAIT.getValue()){
            return Optional.of( "貸出ステータスをキャンセルから貸出待ちには変更できません");
        }
        else if(preStatus == RentalStatus.CANCELED.getValue() && this.status == RentalStatus.RENTAlING.getValue()){
            return Optional.of( "貸出ステータスをキャンセルから貸出中には変更できません");
        }
        else if(preStatus == RentalStatus.CANCELED.getValue() && this.status == RentalStatus.RETURNED.getValue()){
            return Optional.of( "貸出ステータスをキャンセルから返却済みには変更できません");
        }
        else{
            return Optional.empty();
        }
    }
}
