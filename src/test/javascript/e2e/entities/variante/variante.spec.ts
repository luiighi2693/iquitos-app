/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VarianteComponentsPage, VarianteDeleteDialog, VarianteUpdatePage } from './variante.page-object';

const expect = chai.expect;

describe('Variante e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let varianteUpdatePage: VarianteUpdatePage;
    let varianteComponentsPage: VarianteComponentsPage;
    let varianteDeleteDialog: VarianteDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Variantes', async () => {
        await navBarPage.goToEntity('variante');
        varianteComponentsPage = new VarianteComponentsPage();
        expect(await varianteComponentsPage.getTitle()).to.eq('Variantes');
    });

    it('should load create Variante page', async () => {
        await varianteComponentsPage.clickOnCreateButton();
        varianteUpdatePage = new VarianteUpdatePage();
        expect(await varianteUpdatePage.getPageTitle()).to.eq('Create or edit a Variante');
        await varianteUpdatePage.cancel();
    });

    it('should create and save Variantes', async () => {
        const nbButtonsBeforeCreate = await varianteComponentsPage.countDeleteButtons();

        await varianteComponentsPage.clickOnCreateButton();
        await promise.all([
            varianteUpdatePage.setNombreInput('nombre'),
            varianteUpdatePage.setDescripcionInput('descripcion'),
            varianteUpdatePage.setPrecioVentaInput('5'),
            varianteUpdatePage.setPrecioCompraInput('5')
            // varianteUpdatePage.productosSelectLastOption(),
        ]);
        expect(await varianteUpdatePage.getNombreInput()).to.eq('nombre');
        expect(await varianteUpdatePage.getDescripcionInput()).to.eq('descripcion');
        expect(await varianteUpdatePage.getPrecioVentaInput()).to.eq('5');
        expect(await varianteUpdatePage.getPrecioCompraInput()).to.eq('5');
        await varianteUpdatePage.save();
        expect(await varianteUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await varianteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Variante', async () => {
        const nbButtonsBeforeDelete = await varianteComponentsPage.countDeleteButtons();
        await varianteComponentsPage.clickOnLastDeleteButton();

        varianteDeleteDialog = new VarianteDeleteDialog();
        expect(await varianteDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Variante?');
        await varianteDeleteDialog.clickOnConfirmButton();

        expect(await varianteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
