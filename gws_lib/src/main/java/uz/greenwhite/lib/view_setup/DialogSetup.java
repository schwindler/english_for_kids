package uz.greenwhite.lib.view_setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import uz.greenwhite.lib.Command;
import uz.greenwhite.lib.R;
import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.error.AppError;

public class DialogSetup {

    private Object mTitle;
    private Object mMessage;
    private List<Object> names;
    private List<Command> commands;

    public DialogSetup title(CharSequence st) {
        this.mTitle = st;
        return this;
    }

    public DialogSetup title(int stringId) {
        this.mTitle = stringId;
        return this;
    }

    public DialogSetup message(CharSequence st) {
        this.mMessage = st;
        return this;
    }

    public DialogSetup message(int stringId) {
        this.mMessage = stringId;
        return this;
    }

    private DialogSetup optionPrivate(Object name, Command command) {
        if (names == null) {
            names = new ArrayList<Object>();
            commands = new ArrayList<Command>();
        }
        names.add(name);
        commands.add(command);
        return this;
    }

    public DialogSetup option(CharSequence name, Command command) {
        return optionPrivate(name, command);
    }

    public DialogSetup option(int stringId, Command command) {
        return optionPrivate(stringId, command);
    }

    public <T> DialogSetup option(MyArray<T> values, final CommandFacade<T> command) {
        for (final T val : values) {
            optionPrivate(command.getName(val), new Command() {
                @Override
                public void apply() {
                    command.apply(val);
                }
            });
        }
        return this;
    }

    private CharSequence getString(Activity activity, Object val) {
        if (val == null) {
            return "";
        }
        if (val instanceof Integer) {
            return activity.getString((Integer)val);
        }
        return (CharSequence) val;
    }

    public void show(Activity activity, final Command positiveCommand) {
        show(activity, positiveCommand, false);
    }

    public void showAlert(Activity activity, Command positiveCommand) {
        show(activity, positiveCommand, true);
    }

    private void show(Activity activity, final Command positiveCommand, boolean alert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getString(activity, mTitle));
        if (mMessage!= null) {
            builder.setMessage(getString(activity, mMessage));
        }
        if (names != null) {
            if (positiveCommand != null) {
                throw AppError.Unsupported();
            }
            List<CharSequence> ns = new ArrayList<CharSequence>();
            for (Object o : names) {
                ns.add(getString(activity, o));
            }
            ArrayAdapter<CharSequence> adapter =
                    new ArrayAdapter<CharSequence>(activity, android.R.layout.select_dialog_item, ns);
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    commands.get(which).apply();
                }
            });
            builder.setNegativeButton(activity.getString(R.string.cancel), null);
        } else if (positiveCommand != null) {
            if (alert) {
                builder.setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positiveCommand.apply();
                    }
                });
            } else {
                builder.setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positiveCommand.apply();
                    }
                });
                builder.setNegativeButton(activity.getString(R.string.no), null);
            }
        } else {
            builder.setNegativeButton(activity.getString(R.string.ok), null);
        }
        builder.show();
    }

    public void show(Activity activity) {
        show(activity, null);
    }

    public interface CommandFacade<T> {

        CharSequence getName(T val);

        void apply(T val);

    }

}
