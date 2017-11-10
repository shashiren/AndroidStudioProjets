package com.jiekexueyuan.mycontact;

/**
 * Created by stan on 2017/11/9.
 */

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.accountType;


//    #获取联系人方法#
public class ContactManager {

    private MainActivity mainActivity;

    public static List<PhoneInfo> getContact(Context context) {

        
        List<PhoneInfo> contacts = new ArrayList<PhoneInfo>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = context.getContentResolver()
                .query(RawContacts.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

        PhoneInfo contact ;
        while (cursor.moveToNext()) {
//            #注意可能出错
            contact = new PhoneInfo();
            long rawContactId = cursor.getLong(cursor.getColumnIndex(RawContacts._ID));
            contact.setRawContactId(rawContactId);

            Cursor dataCursor = resolver.query(ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.RAW_CONTACT_ID + "=?",
                    new String[]{String.valueOf(rawContactId)},
                    null
            );
            while (dataCursor.moveToNext()) {
                String data1 = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.DATA1));
                String mimetype = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.MIMETYPE));

                if (mimetype.equals(StructuredName.CONTENT_ITEM_TYPE)) {
                    contact.setName(data1);
                } else if (mimetype.equals(Phone.CONTENT_ITEM_TYPE)) {
                    contact.setNumber(data1);
                }

            }
            contacts.add(contact);
            dataCursor.close();


        }
        cursor.close();
        return contacts;

    }

    //    # 添加联系人方法 #
    public static void addContact(Context context,PhoneInfo phoneInfo){
        ContentResolver resolver = context.getContentResolver();

        ContentValues values = new ContentValues();
        Uri rawContactUri = resolver.insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);

        ContentValues values1 = new ContentValues();
        values1.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values1.put(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        values1.put(StructuredName.DISPLAY_NAME, phoneInfo.getName());
        resolver.insert(ContactsContract.Data.CONTENT_URI, values1);

        ContentValues values2 = new ContentValues();
        values2.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values2.put(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        values2.put(Phone.NUMBER, phoneInfo.getNumber());
        resolver.insert(ContactsContract.Data.CONTENT_URI, values2);


    }

    //    #更新联系人方法#
    public static void updateContct(Context context,PhoneInfo phoneInfo){
        ContentResolver resolver = context.getContentResolver();

        ArrayList<ContentProviderOperation> ops =
                new ArrayList<ContentProviderOperation>();

        int rawContactInsertIndex = ops.size();
        ops.add(ContentProviderOperation
                .newInsert(RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_TYPE, accountType)
                .withValue(RawContacts.ACCOUNT_NAME, phoneInfo.getName())
                .build());

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.DISPLAY_NAME, phoneInfo.getName())
                .build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                .withValue(Phone.NUMBER, phoneInfo.getNumber())
                .build());


        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    //    #删除联系人方法#
    public static void deleteContact(Context context,PhoneInfo phoneInfo){
        ContentResolver reslover = context.getContentResolver();
        reslover.delete(RawContacts.CONTENT_URI,RawContacts._ID+"?",
                new String[]{String.valueOf(phoneInfo.getRawContactId())});
    }


}
