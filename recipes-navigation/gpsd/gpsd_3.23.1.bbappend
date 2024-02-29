
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
    file://gpsd.default \
    file://gpsd.socket \
"

do_install:append() {
    install -m 0644 ${WORKDIR}/gpsd.default ${D}/${sysconfdir}/default/gpsd.default
    install -m 0644 ${WORKDIR}/gpsd.socket ${D}${systemd_unitdir}/system/${BPN}.socket
}