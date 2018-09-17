package com.waymaps.data.local.db;

import android.content.Context;

import com.waymaps.contract.Callbacks;
import com.waymaps.data.model.Mail;
import com.waymaps.data.model.PhoneNumber;
import com.waymaps.data.model.Task;
import com.waymaps.util.AppExecutors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AppLocalDataSource {


    private static final String LOG_TAG = AppLocalDataSource.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(AppLocalDataSource.class);

    private static final Object LOCK = new Object();
    private static AppLocalDataSource sInstance;
    private final Context mContext;
    private final AppDatabase mAppDatabase;


    private final AppExecutors mExecutors;

    private AppLocalDataSource(Context context, AppExecutors executors, AppDatabase appDatabase) {
        mContext = context;
        mExecutors = executors;
        mAppDatabase = appDatabase;
    }

    public static AppLocalDataSource getInstance(Context context, AppExecutors executors, AppDatabase appDatabase) {
        LOGGER.debug(LOG_TAG, "Getting the local data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppLocalDataSource(context.getApplicationContext(), executors, appDatabase);
                LOGGER.debug(LOG_TAG, "Made new local data source");
            }
        }
        return sInstance;
    }

    public void getAllPhoneNumber(final Callbacks.Phones.LoadPhonesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<PhoneNumber> phones = mAppDatabase.phoneDao().getPhones();

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!phones.isEmpty()) {
                            callback.onPhonesLoaded(phones);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void addPhoneNumber(final Callbacks.Phones.AddPhoneCallback callback, final PhoneNumber... phoneNumber) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.phoneDao().bulkInsert(phoneNumber);
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess();
                        }
                    });
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void updatePhone(final Callbacks.Phones.EditPhoneCallback callback, final PhoneNumber phoneNumber){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.phoneDao().editPhone(phoneNumber);
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }


    public void deletePhone(final Callbacks.Phones.DeletePhoneCallback callback, final String phoneNumber){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.phoneDao().deleteByPhoneNo(phoneNumber);
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void getAllMails(final Callbacks.Mails.LoadMailsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Mail> mails = mAppDatabase.mailDao().getMails();

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!mails.isEmpty()) {
                            callback.onMailsLoaded(mails);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void addMail(final Callbacks.Mails.AddMailCallback callback, final Mail... mails) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.mailDao().bulkInsert(mails);
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess();
                        }
                    });
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void updateMail(final Callbacks.Mails.EditMailCallback callback, final Mail mail){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.mailDao().editMail(mail);
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }


    public void deleteMail(final Callbacks.Mails.DeleteMailCallback callback, final String mail){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.mailDao().deleteByMail(mail);
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void getAllTasks(final Callbacks.Tasks.LoadTasksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mAppDatabase.taskDao().getTaskHistory();

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!tasks.isEmpty()) {
                            callback.onTasksLoaded(tasks);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void addTask(final Callbacks.Tasks.AddTaskCallback callback, final Task... tasks) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.taskDao().bulkInsert(tasks);
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess();
                        }
                    });
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void updateTask(final Callbacks.Tasks.EditTaskCallback callback, final Task task){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.taskDao().ediTask(task);
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }


    public void deleteAllTasksWithStatus(final Callbacks.Tasks.DeleteTaskCallback callback, final int status){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mAppDatabase.taskDao().deleteAllTaskByStatus(status);
                } catch (Exception e) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }



}
