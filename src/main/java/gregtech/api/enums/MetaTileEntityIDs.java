package gregtech.api.enums;

/*
 * No more magic numbers about TE's IDs. Yay!!!
 */
public enum MetaTileEntityIDs {

    ALLOY_SMELTER_LV(201),
    ALLOY_SMELTER_MV(202),
    ALLOY_SMELTER_HV(203),
    ALLOY_SMELTER_EV(204),
    ALLOY_SMELTER_IV(205),
    ASSEMBLER_LV(211),
    ASSEMBLER_MV(212),
    ASSEMBLER_HV(213),
    ASSEMBLER_EV(214),
    ASSEMBLER_IV(215),
    BENDING_MACHINE_LV(221),
    BENDING_MACHINE_MV(222),
    BENDING_MACHINE_HV(223),
    BENDING_MACHINE_EV(224),
    BENDING_MACHINE_IV(225),
    CANNER_LV(231),
    CANNER_MV(232),
    CANNER_HV(233),
    CANNER_EV(234),
    CANNER_IV(235),
    LATHE_LV(291),
    LATHE_MV(292),
    LATHE_HV(293),
    LATHE_EV(294),
    LATHE_IV(295),
    MACERATOR_LV(301),
    MACERATOR_MV(302),
    MACERATOR_HV(303),
    MACERATOR_EV(304),
    MACERATOR_IV(305),
    MICROWAVE_OVEN_LV(311),
    MICROWAVE_OVEN_MV(312),
    MICROWAVE_OVEN_HV(313),
    MICROWAVE_OVEN_EV(314),
    MICROWAVE_OVEN_IV(315),
    PRINTER_LV(321),
    PRINTER_MV(322),
    PRINTER_HV(323),
    PRINTER_EV(324),
    PRINTER_IV(325),
    PRINTER_LuV(326),
    PRINTER_ZPM(327),
    PRINTER_UV(328),
    RECYCLER_LV(331),
    RECYCLER_MV(332),
    RECYCLER_HV(333),
    RECYCLER_EV(334),
    RECYCLER_IV(335),
    WIREMILL_LV(351),
    WIREMILL_MV(352),
    WIREMILL_HV(353),
    WIREMILL_EV(354),
    WIREMILL_IV(355),
    CENTRIFUGE_LV(361),
    CENTRIFUGE_MV(362),
    CENTRIFUGE_HV(363),
    CENTRIFUGE_EV(364),
    CENTRIFUGE_IV(365),
    THERMAL_CENTRIFUGE_LV(381),
    THERMAL_CENTRIFUGE_MV(382),
    THERMAL_CENTRIFUGE_HV(383),
    THERMAL_CENTRIFUGE_EV(384),
    THERMAL_CENTRIFUGE_IV(385),
    ORE_WASHER_LV(391),
    ORE_WASHER_MV(392),
    ORE_WASHER_HV(393),
    ORE_WASHER_EV(394),
    ORE_WASHER_IV(395),
    UNPACKAGER_LV(411),
    UNPACKAGER_MV(412),
    UNPACKAGER_HV(413),
    UNPACKAGER_EV(414),
    UNPACKAGER_IV(415),
    UNPACKAGER_LuV(416),
    UNPACKAGER_ZPM(417),
    UNPACKAGER_UV(418),
    MATTER_AMPLIFIER_LV(471),
    MATTER_AMPLIFIER_MV(472),
    MATTER_AMPLIFIER_HV(473),
    MATTER_AMPLIFIER_EV(474),
    MATTER_AMPLIFIER_IV(475),
    CHEMICAL_BATH_LV(541),
    CHEMICAL_BATH_MV(542),
    CHEMICAL_BATH_HV(543),
    CHEMICAL_BATH_EV(544),
    CHEMICAL_BATH_IV(545),
    POLARIZER_LV(551),
    POLARIZER_MV(552),
    POLARIZER_HV(553),
    POLARIZER_EV(554),
    POLARIZER_IV(555),
    AUTOCLAVE_LV(571),
    AUTOCLAVE_MV(572),
    AUTOCLAVE_HV(573),
    AUTOCLAVE_EV(574),
    AUTOCLAVE_IV(575),
    MIXER_LV(581),
    MIXER_MV(582),
    MIXER_HV(583),
    MIXER_EV(584),
    MIXER_IV(585),
    LASER_ENGRAVER_LV(591),
    LASER_ENGRAVER_MV(592),
    LASER_ENGRAVER_HV(593),
    LASER_ENGRAVER_EV(594),
    LASER_ENGRAVER_IV(595),
    SLICER_LV(631),
    SLICER_MV(632),
    SLICER_HV(633),
    SLICER_EV(634),
    SLICER_IV(635),
    SIFTER_LV(641),
    SIFTER_MV(642),
    SIFTER_HV(643),
    SIFTER_EV(644),
    SIFTER_IV(645),
    ARC_FURNACE_LV(651),
    ARC_FURNACE_MV(652),
    ARC_FURNACE_HV(653),
    ARC_FURNACE_EV(654),
    ARC_FURNACE_IV(655),
    PLASMA_ARC_FURNACE_LV(661),
    PLASMA_ARC_FURNACE_MV(662),
    PLASMA_ARC_FURNACE_HV(663),
    PLASMA_ARC_FURNACE_EV(664),
    PLASMA_ARC_FURNACE_IV(665),

    OVEN_LV(671),
    OVEN_MV(672),
    OVEN_HV(673),
    OVEN_EV(674),
    OVEN_IV(675),
    ;

    public final int ID;

    private MetaTileEntityIDs(int ID) {
        this.ID = ID;
    }
}
