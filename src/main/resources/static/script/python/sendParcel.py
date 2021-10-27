# import the necessary packages

import argparse
import json
import os
import shutil

import requests

from ballTracking import take_pic


def sendPicToApi(pid, signeePhoto, signeeSignaturePhoto):
    url = "http://localhost:9001/parcels/complete-delivery"

    payload = json.dumps({
        "pid": pid,
        "signeePhoto": os.path.abspath(signeePhoto),
        "signeeSignaturePhoto": os.path.abspath(signeeSignaturePhoto)
    }, sort_keys=True, indent=4, separators=(',', ': '))
    headers = {
        'Content-Type': 'application/json'
    }

    response = requests.request("PATCH", url, headers=headers, data=payload)

    print(response.text.encode('utf8'))


def build_file_path(uuid, image_type):
    base_file_pattern = "./images/{}/{}.jpg"
    return base_file_pattern.format(uuid, image_type)


def create_image_directories(uuid):
    base_path = "images/{}/"
    target_path = base_path.format(uuid)

    if os.path.isdir(target_path):
        shutil.rmtree(target_path)

    os.makedirs(target_path)


if __name__ == '__main__':
    # sendPicToApi("8470ad4f-38a6-4ca7-af5c-e72c7a29fded", "images/01/01.jpg", "images/02/01.jpg")
    ap = argparse.ArgumentParser()
    ap.add_argument("--pid", help="Parcel ID")
    args = vars(ap.parse_args())
    pid = args["pid"]
    create_image_directories(pid)
    signee_photo = build_file_path(pid, "signee_image")
    signee_signature_photo = build_file_path(pid, "signee_signature_image")
    take_pic(signee_photo, signee_signature_photo)
    sendPicToApi(pid, signee_photo, signee_signature_photo)
