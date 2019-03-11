package com.rafaeldbl.xptosystems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CsvCity {

    private Long ibge_id;

    private String uf;

    private String name;

    private String no_accents;

    private String alternative_names;

    private String microregion;

    private String mesoregion;

    private boolean capital;

    private Float lat;

    private Float lon;
}
