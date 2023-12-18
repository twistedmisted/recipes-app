package ua.kpi.klopotenkoapp.contract.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBO<T> {

    private List<T> content = new ArrayList<>();

    private int currentPageNumber;

    private int totalPages;

    public boolean hasContent() {
        return !this.content.isEmpty();
    }
}
