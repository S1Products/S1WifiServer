/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class info_s1products_midi_MidiSender */

#ifndef _Included_info_s1products_midi_MidiSender
#define _Included_info_s1products_midi_MidiSender
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     info_s1products_midi_MidiSender
 * Method:    jni_initialize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_info_s1products_midi_MidiSender_jni_1initialize
  (JNIEnv *, jobject);

/*
 * Class:     info_s1products_midi_MidiSender
 * Method:    jni_finalize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_info_s1products_midi_MidiSender_jni_1finalize
  (JNIEnv *, jobject);

/*
 * Class:     info_s1products_midi_MidiSender
 * Method:    jni_open
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_info_s1products_midi_MidiSender_jni_1open
  (JNIEnv *, jobject);

/*
 * Class:     info_s1products_midi_MidiSender
 * Method:    jni_close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_info_s1products_midi_MidiSender_jni_1close
  (JNIEnv *, jobject);

/*
 * Class:     info_s1products_midi_MidiSender
 * Method:    jni_sendMessage
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_info_s1products_midi_MidiSender_jni_1sendMessage
  (JNIEnv *, jobject, jbyteArray);

#ifdef __cplusplus
}
#endif
#endif