SUMMARY = "Console donkeycar car image"
HOMEPAGE = "http://zuckerman.dev"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit image

CORE_OS = " \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    term-prompt \
    tzdata \
"

EXTRA_TOOLS = " \
    bzip2 \
    coreutils \
    curl \
    diffutils \
    dosfstools \
    e2fsprogs-mke2fs \
    ethtool \
    fbset \
    findutils \
    grep \
    i2c-tools \
    ifupdown \
    hostapd \
    bluez5 \
    bridge-utils \
    dhcpcd \
    iperf3 \
    iproute2 \
    iptables \
    less \
    lsof \
    ntp ntp-tickadj \
    parted \
    procps \
    rndaddtoentcnt \
    sysfsutils \
    tcpdump \
    util-linux \
    util-linux-blkid \
    unzip \
    wget \
    zip \
    python3-donkeycar \
    python3-opencv \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${EXTRA_TOOLS} \
"

IMAGE_FILE_BLACKLIST += " \
    /etc/init.d/hwclock.sh \
 "

remove_blacklist_files() {
    for i in ${IMAGE_FILE_BLACKLIST}; do
        rm -rf ${IMAGE_ROOTFS}$i
    done
}

set_local_timezone() {
    ln -sf /usr/share/zoneinfo/EST5EDT ${IMAGE_ROOTFS}/etc/localtime
}

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

disable_rng_daemon() {
    rm -f ${IMAGE_ROOTFS}/etc/rcS.d/S*rng-tools
    rm -f ${IMAGE_ROOTFS}/etc/rc5.d/S*rng-tools
}

create_opt_dir() {
    mkdir -p ${IMAGE_ROOTFS}/opt
}

ROOTFS_POSTPROCESS_COMMAND += " \
    remove_blacklist_files ; \
    set_local_timezone ; \
    disable_bootlogd ; \
    disable_rng_daemon ; \
    create_opt_dir ; \
"

DEPENDS += "rpi-bootfiles"

WIFI = " \
    crda \
    iw \
    linux-firmware-rpidistro-bcm43430 \
    linux-firmware-rpidistro-bcm43455 \
    wpa-supplicant \
"

RPI_STUFF = " \
    userland \
    spidev-test \
"

GPS_STUFF = " \
    gpsd \
    gps-utils \
    geoclue \
"

ADDONS = " \
    python3 \
"

SW_UPDATE = " \
    swupdate \
    swupdate-tools \
    libubootenv-bin \
"

IMAGE_INSTALL += " \
    firewall \
    ${ADDONS} \
    ${GPS_STUFF} \
    ${RPI_STUFF} \
    ${WIFI} \
    ${SW_UPDATE} \
"

TOOLCHAIN_TARGET_TASK += " \
    kernel-devsrc \
    pkgconfig \
    "

IMAGE_ROOTFS_MAXSIZE = "2097152"

export IMAGE_BASENAME = "donkeycar-image"
