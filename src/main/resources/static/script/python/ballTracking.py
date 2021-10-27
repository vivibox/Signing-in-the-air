# import the necessary packages
from collections import deque
from imutils.video import VideoStream
import numpy as np
import argparse
import cv2
import imutils
import time


def take_pic(signee_photo, signee_signature_photo):
    # construct the argument parse and parse the arguments
    ap = argparse.ArgumentParser()
    ap.add_argument("--pid", help="Parcel ID")
    ap.add_argument("-v", "--video", help="path to the (optional) video file")
    ap.add_argument("-b", "--buffer", type=int, default=64, help="max buffer size")
    args = vars(ap.parse_args())

    # define the lower and upper boundaries of the "green"
    # ball in the HSV color space, then initialize the
    # list of tracked points
    # YELLOW (18, 120, 200) (64, 255, 255)
    greenLower = (35, 43, 46)
    greenUpper = (88, 255, 255)
    pts = deque(maxlen=args["buffer"])
    # if a video path was not supplied, grab the reference
    # to the webcam
    if not args.get("video", False):
        vs = VideoStream(src=0).start()
    # otherwise, grab a reference to the video file
    else:
        vs = cv2.VideoCapture(args["video"])
    # allow the camera or video file to warm up
    time.sleep(2.0)

    # Record sign-action
    listSign = []
    # Number of pic
    picNum = 0

    # keep looping
    while True:
        # grab the current frame
        frame = vs.read()
        # handle the frame from VideoCapture or VideoStream
        frame = frame[1] if args.get("video", False) else frame
        # if we are viewing a video and we did not grab a frame,
        # then we have reached the end of the video
        if frame is None:
            break
        # resize the frame, blur it, and convert it to the HSV
        # color space
        frame = imutils.resize(frame, width=600)
        blurred = cv2.GaussianBlur(frame, (11, 11), 0)
        hsv = cv2.cvtColor(blurred, cv2.COLOR_BGR2HSV)
        # construct a mask for the color "green", then perform
        # a series of dilations and erosions to remove any small
        # blobs left in the mask
        mask = cv2.inRange(hsv, greenLower, greenUpper)
        mask = cv2.erode(mask, None, iterations=2)
        mask = cv2.dilate(mask, None, iterations=2)

        # find contours in the mask and initialize the current
        # (x, y) center of the ball
        cnts = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL,
                                cv2.CHAIN_APPROX_SIMPLE)
        cnts = imutils.grab_contours(cnts)
        center = None
        # only proceed if at least one contour was found
        if len(cnts) > 0:
            # find the largest contour in the mask, then use
            # it to compute the minimum enclosing circle and
            # centroid
            c = max(cnts, key=cv2.contourArea)
            ((x, y), radius) = cv2.minEnclosingCircle(c)
            M = cv2.moments(c)
            center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
            # only proceed if the radius meets a minimum size
            if radius > 10:
                # draw the circle and centroid on the frame,
                # then update the list of tracked points
                cv2.circle(frame, (int(x), int(y)), int(radius),
                           (0, 255, 255), 2)
                cv2.circle(frame, center, 5, (0, 0, 255), -1)
        # update the points queue
        pts.appendleft(center)

        # 繪製檢測到球的過去  N  （x，y）坐標
        # loop over the set of tracked points

        # 設置畫布
        imgSign = np.zeros((400, 600, 3), np.uint8)

        for i in range(1, len(pts)):
            # if either of the tracked points are None, ignore
            # them
            if pts[i - 1] is None or pts[i] is None:
                continue
            # otherwise, compute the thickness of the line and
            # draw the connecting lines
            thickness = int(np.sqrt(args["buffer"] / float(i + 1)) * 2.5)
            # cv.line(img,起點座標，終點座標，顏色，粗細)
            cv2.line(frame, pts[i - 1], pts[i], (0, 0, 255), thickness)
            listSign.append([pts[i - 1], pts[i]])
            # 繪製圖像
            cv2.line(imgSign, pts[i - 1], pts[i], (0, 0, 255), thickness)
        # show the frame to our screen
        # 水平翻轉
        flippedImg = cv2.flip(imgSign, 1)
        # 彩色背景水平翻轉
        flippedBackground = cv2.flip(frame, 1)
        cv2.imshow("Frame", flippedBackground)
        cv2.imshow("imgSign", flippedImg)

        # 定義key
        key = cv2.waitKey(60) & 0xFF

        # 按p拍照
        if key == ord("p"):
            listSign = []
            cv2.imwrite('images/background' + str(picNum) + '.jpg', flippedBackground)
            print("Pic already!!!")
        # if the 'r' key is pressed, record the signature and picture
        if key == ord("r"):
            # 設置畫布
            imgSign = np.zeros((400, 600, 3), np.uint8)
            BG = cv2.imread('images/background' + str(picNum) + '.jpg')
            for i in range(len(listSign)):
                # 繪製圖像
                # cv2.line(imgSign2, listSign[i][0], listSign[i][1], (0, 0, 255), thickness)
                # 抓取之前的彩色背景照片來貼上
                cv2.line(BG, listSign[i][0], listSign[i][1], (0, 0, 255), thickness)
                cv2.line(imgSign, listSign[i][0], listSign[i][1], (0, 0, 255), thickness)
                # 水平翻轉
                # flippedImg2 = cv2.flip(imgSign2, 1)
                # 將彩色背景照片水平翻轉
                flippedBackground2 = cv2.flip(BG, 1)
                flippedImg = cv2.flip(imgSign, 1)
            cv2.imwrite(signee_signature_photo, flippedBackground2)
            cv2.imwrite(signee_photo, flippedImg)
            print("Picture record ~~")
            picNum += 1
            # break
            break
        # if the 'q' key is pressed, stop the loop
        if key == ord("q"):
            print("Break--")
            break
    # if we are not using a video file, stop the camera video stream
    if not args.get("video", False):
        vs.stop()
    # otherwise, release the camera
    else:
        vs.release()
    # close all windows
    cv2.destroyAllWindows()
    return "seccess~~~~~~~~~~~~~"
