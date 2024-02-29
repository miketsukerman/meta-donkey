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
    python3-donkeycarcar \
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

DEV_EXTRAS = " \
    serialecho  \
    spiloop \
"

RPI_STUFF = " \
    userland \
    spidev-test \
    pijuiceboot \
    python3-pijuice \
    pijuice-firmware \
    python3-urwid \
    python3-smbus \
"

GEO_STUFF = " \
    adc-read \
    geod \
    miniseed \
"

GPS_STUFF = " \
    gpsd \
    gps-utils \
    geoclue \
"

POWER_MANAGEMENT = " \
    nut \
    collectd \
"

ADDONS = " \
    python3 \
    nginx \
"

SW_UPDATE = " \
    swupdate \
    swupdate-tools \
    libubootenv-bin \
"

IMAGE_INSTALL += " \
    firewall \
    ${ADDONS} \
    ${POWER_MANAGEMENT} \ 
    ${GPS_STUFF} \
    ${DEV_EXTRAS} \
    ${RPI_STUFF} \
    ${WIFI} \
    ${SW_UPDATE} \
"

TOOLCHAIN_TARGET_TASK += " \
    kernel-devsrc \
    pkgconfig \
    concurrentqueue-dev \
    cppzmq-dev \
    zeromq-staticdev \
    "
TOOLCHAIN_HOST_TASK += " \
    nativesdk-perl \
    nativesdk-perl-module-lib \
    nativesdk-perl-module-base \
    nativesdk-perl-module-bytes \
    nativesdk-perl-module-data-dumper \
    nativesdk-perl-module-digest-md5 \
    nativesdk-perl-module-file-spec \
    nativesdk-perl-module-file-spec-functions \
    nativesdk-perl-module-file-spec-win32 \
    nativesdk-perl-module-findbin \
    nativesdk-perl-module-getopt-long \
    nativesdk-perl-module-pod-text \
    nativesdk-perl-module-getopt-std \
    "

IMAGE_ROOTFS_MAXSIZE = "2097152"

export IMAGE_BASENAME = "donkeycar-image"
